package site.fitme.batch.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.fitme.batch.domain.product.dto.ProductRankingResponse;
import site.fitme.batch.domain.product.entity.Product;
import site.fitme.batch.repository.OrderProductRepository;
import site.fitme.batch.repository.ProductLikeRepository;
import site.fitme.batch.repository.ProductRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class RankingScheduler {

    private static final String RANKING_NAME = "product:rankings";
    private static final String RANKING_UPDATE_TIME_NAME = "updateTime";
    private static final int RANKING_SIZE = 200;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ProductLikeRepository productLikeRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Scheduled(fixedRate = 1800000) // 30분마다 실행
    public void updateRankings() {

        // 200개 상품 랭킹 계산
        List<ProductRankingResponse> rankings = calculateRankings();

        // 전체 랭킹 리스트를 JSON 문자열로 직렬화
        try {
            String rankingsJson = objectMapper.writeValueAsString(rankings);
            redisTemplate.opsForValue().set(RANKING_NAME, rankingsJson);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert rankings to JSON", e);
            throw new RuntimeException("Rankings JSON conversion failed", e);
        }

        // 업데이트 시간 저장
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        redisTemplate.opsForValue().set(RANKING_UPDATE_TIME_NAME, formattedDateTime);
        log.info("Product rankings updated at {}", formattedDateTime);
    }

    private List<ProductRankingResponse> calculateRankings() {
        // 일주일
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        Map<Long, Double> scores = new HashMap<>();
        // 구매 8
        orderProductRepository.findScoresForOrderProductsSince(oneWeekAgo)
            .forEach(score -> scores.merge(score.getProductId(), score.getScore(), Double::sum));
        // 좋아요 2
        productLikeRepository.findScoresForProductLikesSince(oneWeekAgo)
            .forEach(score -> scores.merge(score.getProductId(), score.getScore(), Double::sum));

        // 점수에 따라 상위 200 개 상품 정렬
        List<Long> topProductIds = scores.entrySet().stream()
            .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
            .limit(RANKING_SIZE)
            .map(Map.Entry::getKey)
            .toList();

        // 데이터베이스에서 한 번에 상품 조회
        List<Product> topProducts = productRepository.findByIdIn(topProductIds);

        // 조회된 상품을 원래 순서대로 재정렬
        Map<Long, Integer> idToRank = IntStream.range(0, topProductIds.size())
            .boxed()
            .collect(Collectors.toMap(topProductIds::get, rank -> rank + 1));

        return topProducts.stream()
            .sorted(Comparator.comparingInt(product -> idToRank.get(product.getId())))
            .map(product -> {
                int rank = idToRank.get(product.getId());
                return ProductRankingResponse.of(rank, product);
            })
            .collect(Collectors.toList());
    }
}
