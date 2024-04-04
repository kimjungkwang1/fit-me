package site.fitme.batch.job;

import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import site.fitme.batch.entity.product.Product;
import site.fitme.batch.repository.OrderProductRepository;
import site.fitme.batch.repository.ProductLikeRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    private final ProductLikeRepository productLikeRepository;
    private final OrderProductRepository orderProductRepository;
    private Map<Long, Double> scores;


    @Value("${chunkSize:100}")
    private int chunkSize;

    // Job 정의
    @Bean
    public Job calculatePopularityScoreJob() {
        log.info(">>>>>>>>>>>>>>>>>>> job define date = {}", LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return new JobBuilder("calculatePopularityScoreJob", jobRepository)
            .start(initializeScoresStep())
            .next(calculateScoreStep(null))
            .next(updateScoreStep(null))
            .build();
    }

    // Step 정의
    // scores 초기화 Step 정의
    @Bean
    public Step initializeScoresStep() {
        return new StepBuilder("initializeScoresStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                scores = new HashMap<>(); // scores 맵 초기화
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }


    @Bean
    @JobScope
    public Step calculateScoreStep(@Value("#{jobParameters['date']}") String date) {
        log.info(">>>>>>>>>> calculate score step date = {}", date);
        return new StepBuilder("calculateScoreStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                // 점수 계산
                LocalDateTime oneMonthAgo = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusMonths(1);
                orderProductRepository.findScoresForOrderProductsSince(oneMonthAgo)
                    .forEach(score -> scores.merge(score.getProductId(), score.getScore(), Double::sum));
                productLikeRepository.findScoresForProductLikesSince(oneMonthAgo)
                    .forEach(score -> scores.merge(score.getProductId(), score.getScore(), Double::sum));

                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }

    @Bean
    @JobScope
    public Step updateScoreStep(@Value("#{jobParameters['date']}") String date) {
        log.info(">>>>>>>>>> update score step date = {}", date);
        return new StepBuilder("updateScoreStep", jobRepository)
            .<Product, Product>chunk(chunkSize, transactionManager)
            .reader(productItemReader(entityManagerFactory))
            .processor(productItemProcessor())
            .writer(productItemWriter(entityManagerFactory))
            .build();
    }

    @Bean
    public JpaPagingItemReader<Product> productItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Product>()
            .name("productItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT p FROM Product p")
            .pageSize(chunkSize)
            .build();
    }


    @Bean
    @StepScope // @StepScope 어노테이션을 사용하면 런타임에 StepExecution에서 필요한 정보를 가져올 수 있음
    public ItemProcessor<Product, Product> productItemProcessor() {
        return product -> {
            Double score = scores.getOrDefault(product.getId(), 0.0);
            product.updateMonthlyPopularityScore(score);
            return product;
        };
    }

    @Bean
    public JpaItemWriter<Product> productItemWriter(EntityManagerFactory entityManagerFactory) {
        // 변경된 상품 목록을 받아 데이터베이스에 반영
        JpaItemWriter<Product> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

}

