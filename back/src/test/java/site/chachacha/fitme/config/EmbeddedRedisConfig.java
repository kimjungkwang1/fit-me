package site.chachacha.fitme.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

@Slf4j
@Configuration
public class EmbeddedRedisConfig {
    private final RedisServer redisServer;

    public EmbeddedRedisConfig(@Value("${spring.redis.port}") int port, @Value("${spring.redis.maxmemory}") String maxMemory) {
        this.redisServer = RedisServer.builder()
            .port(port)
            .setting("maxmemory " + maxMemory + "M")
            .build();
    }

    @PostConstruct
    public void startRedis() {
        try {
            this.redisServer.start();
        }
        catch (Exception e) {
            log.error("Embedded Redis Server Start Fail", e);
        }
    }

    @PreDestroy
    public void stopRedis() {
        this.redisServer.stop();
    }
}
