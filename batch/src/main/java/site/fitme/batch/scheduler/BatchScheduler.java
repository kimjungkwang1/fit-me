package site.fitme.batch.scheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job calculatePopularityScoreJob;

    // 매일 새벽 5시에 실행
//    @Scheduled(cron = "0 0 5 * * ?")
    @Scheduled(cron = "0 00 14 * * ?")
    public void runJob() {
        try {
            jobLauncher.run(calculatePopularityScoreJob, new JobParametersBuilder()
                .addString("date", LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .toJobParameters());
        } catch (Exception e) {
            log.error("Job execution failed: ", e);
        }
    }
}
