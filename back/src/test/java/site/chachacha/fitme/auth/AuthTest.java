package site.chachacha.fitme.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import site.chachacha.fitme.domain.auth.service.JwtService;

@TestInstance(PER_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@TestMethodOrder(value = OrderAnnotation.class)
public class AuthTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String refreshToken;

    @BeforeAll
    void setUp() {
        // 테스트용 데이터베이스 초기화
        try (Connection connection = dataSource.getConnection()) {
            executeSqlScript(connection,
                new FileSystemResource("src/test/resources/sql/member.sql"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // RefreshToken을 Redis에 저장한다.
        refreshToken = jwtService.issueJwts(1L)[1];
        jwtService.saveRefreshTokenToRedis(refreshToken, 1L);
    }

    @Test
    @Order(200)
    @DisplayName("Reissue 성공 테스트")
    void reissueTest() throws Exception {
        // when
        // reissue API에 요청을 보낸다.
        MvcResult result = mockMvc.perform(post("/api/auth/reissue/v1")
                .header("AuthorizationRefresh", "Bearer " + refreshToken)
                .header("Content-Type", "application/json"))

            // then
            // 200인지 확인
            .andExpect(status().isOk()).andReturn();

        // 응답 헤더에 있는 RefreshToken 꺼내기
        String refreshToken = result.getResponse().getHeader("AuthorizationRefresh")
            .replace("Bearer ", "");

        this.refreshToken = refreshToken;

        String redisKey = JwtService.REFRESH_TOKEN_REDIS_KEY + refreshToken;

        // RefreshToken이 Redis에 있는지 확인
        String memberId = redisTemplate.opsForValue().get(redisKey);

        // RefreshToken의 값이 같은지 확인
        assertThat(memberId).isEqualTo("1");

        // AccessToken이 새로 발급되었는지 확인
        assertThat(result.getResponse().getHeader("Authorization")).isNotEqualTo(null);
    }

    @Test
    @Order(201)
    @DisplayName("Reissue 실패 테스트 - 요청에 RefreshToken이 없을 때")
    void reissueFailTest1() throws Exception {
        // when
        // reissue API에 요청을 보낸다.
        // RefreshToken이 없는 상태로 요청을 보낸다.
        MvcResult result = mockMvc.perform(post("/api/auth/reissue/v1")
                .header("Content-Type", "application/json"))

            // then
            // 400인지 확인
            .andExpect(status().isBadRequest()).andReturn();

        // 응답 헤더에 AccessToken과 RefreshToken이 없어야 한다.
        assertThat(result.getResponse().getHeader("Authorization")).isEqualTo(null);
        assertThat(result.getResponse().getHeader("AuthorizationRefresh")).isEqualTo(null);
    }

    @Test
    @Order(202)
    @DisplayName("Reissue 실패 테스트 - 요청에 있는 RefreshToken에 오류가 있을 때")
    void reissueFailTest2() throws Exception {
        // when
        // reissue API에 요청을 보낸다.
        // 오류가 있는 RefreshToken을 요청에 실어서 보낸다.
        MvcResult result = mockMvc.perform(post("/api/auth/reissue/v1")
                .header("AuthorizationRefresh", "Bearer " + refreshToken + "error")
                .header("Content-Type", "application/json"))

            // then
            // 400인지 확인
            .andExpect(status().isUnauthorized()).andReturn();

        // 응답 헤더에 AccessToken과 RefreshToken이 없어야 한다.
        assertThat(result.getResponse().getHeader("Authorization")).isEqualTo(null);
        assertThat(result.getResponse().getHeader("AuthorizationRefresh")).isEqualTo(null);
    }

    @Test
    @Order(203)
    @DisplayName("Reissue 실패 테스트 - Redis에 해당 RefreshToken이 없을 때")
    void reissueFailTest3() throws Exception {
        // given
        // Redis에 해당 RefreshToken을 삭제한다.
        jwtService.deleteRefreshTokenFromRedis(refreshToken);

        // when
        // reissue API에 요청을 보낸다.
        MvcResult result = mockMvc.perform(post("/api/auth/reissue/v1")
                .header("AuthorizationRefresh", "Bearer " + refreshToken)
                .header("Content-Type", "application/json"))

            // then
            // 401인지 확인
            .andExpect(status().isUnauthorized()).andReturn();

        // 응답 헤더에 AccessToken과 RefreshToken이 없어야 한다.
        assertThat(result.getResponse().getHeader("Authorization")).isEqualTo(null);
        assertThat(result.getResponse().getHeader("AuthorizationRefresh")).isEqualTo(null);
    }
}
