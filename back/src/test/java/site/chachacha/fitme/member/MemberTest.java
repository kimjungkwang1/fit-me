package site.chachacha.fitme.member;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.TransactionSystemException;
import site.chachacha.fitme.auth.service.JwtService;
import site.chachacha.fitme.member.dto.MemberResponse;
import site.chachacha.fitme.member.dto.MemberUpdate;
import site.chachacha.fitme.member.service.MemberService;

@TestInstance(PER_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@TestMethodOrder(value = OrderAnnotation.class)
public class MemberTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken, refreshToken;

    @BeforeAll
    void setUp() {
        // 테스트용 데이터베이스 초기화
        try (Connection connection = dataSource.getConnection()) {
            executeSqlScript(connection, new FileSystemResource("src/test/resources/sql/data-h2.sql"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // RefreshToken을 Redis에 저장한다.
        String[] jwts = jwtService.issueJwts(1L);
        accessToken = jwts[0];
        refreshToken = jwts[1];
        jwtService.saveRefreshTokenToRedis(refreshToken, 1L);
    }

    @Test
    @Order(200)
    @DisplayName("회원 정보 조회 성공")
    void getMemberInfoSuccess() throws Exception {
        // when
        // 회원 정보 조회
        MvcResult result = mockMvc.perform(get("/api/members")
            .header("Authorization", "Bearer " + accessToken)
            .header("Content-Type", "application/json"))
            
        // then
        // 200인지 확인
        .andExpect(status().isOk()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);

        MemberResponse memberInfo = objectMapper.readValue(body, MemberResponse.class);

        // then
        assertThat(memberInfo.getNickname()).isEqualTo("Cha Cha");
        assertThat(memberInfo.getGender()).isNull();
    }

    @Test
    @Order(300)
    @DisplayName("회원 정보 수정 성공")
    void updateMemberInfoSuccess1() throws Exception {
        // given
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("Cha Cha Cha")
            .phoneNumber("01085783088")
            .birthYear(1997)
            .address("서울특별시 강남구")
            .build();

        // when
        // 회원 정보 수정
        MvcResult result1 = mockMvc.perform(post("/api/members")
            .header("Authorization", "Bearer " + accessToken)
            .header("Content-Type", "application/json")
            .content(objectMapper.writeValueAsString(memberUpdate)))
            
        // then
        .andExpect(status().isNoContent()).andReturn();

        // then
        // 수정된 회원 정보 조회
        MvcResult result2 = mockMvc.perform(get("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json"))
            .andExpect(status().isOk()).andReturn();
        
        String body = result2.getResponse().getContentAsString(UTF_8);
        MemberResponse memberInfo = objectMapper.readValue(body, MemberResponse.class);

        assertThat(memberInfo.getNickname()).isEqualTo("Cha Cha Cha");
        assertThat(memberInfo.getGender()).isNull();
        assertThat(memberInfo.getPhoneNumber()).isEqualTo("01085783088");
        assertThat(memberInfo.getBirthYear()).isEqualTo(1997);
        assertThat(memberInfo.getAddress()).isEqualTo("서울특별시 강남구");
    }

    @Test
    @Order(310)
    @DisplayName("회원 정보 수정 실패 - 닉네임이 없을 때")
    void updateMemberInfoFail1() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("")
            .build();
        
        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("닉네임을 입력해주세요.");
    }

    @Test
    @Order(311)
    @DisplayName("회원 정보 수정 실패 - 닉네임이 null일 때")
    void updateMemberInfoFail1_1() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .phoneNumber("01085783088")
            .build();
        
        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("닉네임을 입력해주세요.");
    }

    @Test
    @Order(320)
    @DisplayName("회원 정보 수정 실패 - 닉네임이 2자 미만일 때")
    void updateMemberInfoFail2() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("a")
            .build();

        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("닉네임은 2자 이상 30자 이하로 입력해주세요.");
    }

    @Test
    @Order(330)
    @DisplayName("회원 정보 수정 실패 - 닉네임이 30자 초과일 때")
    void updateMemberInfoFail3() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("a".repeat(31))
            .build();

        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("닉네임은 2자 이상 30자 이하로 입력해주세요.");
    }

    @Test
    @Order(340)
    @DisplayName("회원 정보 수정 실패 - 휴대폰 번호가 20자 초과일 때")
    void updateMemberInfoFail4() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("Cha Cha Cha")
            .phoneNumber("0".repeat(21))
            .build();

        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("휴대폰 번호는 20자 이하로 입력해주세요.");
    }

    @Test
    @Order(350)
    @DisplayName("회원 정보 수정 실패 - 휴대폰 번호가 숫자가 아닐 때")
    void updateMemberInfoFail5() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("Cha Cha Cha")
            .phoneNumber("01085d83088a")
            .build();

        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("휴대폰 번호는 숫자만 입력해주세요.");
    }

    @Test
    @Order(360)
    @DisplayName("회원 정보 수정 실패 - 태어난 년도가 2024 초과일 때")
    void updateMemberInfoFail6() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("Cha Cha Cha")
            .birthYear(2025)
            .build();

        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("태어난 년도를 다시 확인해주세요.");
    }

    @Test
    @Order(361)
    @DisplayName("회원 정보 수정 실패 - 태어난 년도가 1900 미만일 때")
    void updateMemberInfoFail6_1() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("Cha Cha Cha")
            .birthYear(1899)
            .build();

        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("태어난 년도를 다시 확인해주세요.");
    }

    @Test
    @Order(370)
    @DisplayName("회원 정보 수정 실패 - 주소가 200자 초과일 때")
    void updateMemberInfoFail7() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("Cha Cha Cha")
            .address("a".repeat(201))
            .build();

        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("주소는 2자 이상, 200자 이하로 입력해주세요.");
    }

    @Test
    @Order(371)
    @DisplayName("회원 정보 수정 실패 - 주소가 2자 미만일 때")
    void updateMemberInfoFail7_1() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("Cha Cha Cha")
            .address("a")
            .build();

        // when
        // 회원 정보 수정
        MvcResult result = mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isBadRequest()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        assertThat(body).contains("주소는 2자 이상, 200자 이하로 입력해주세요.");
    }

    @Test
    @Order(380)
    @DisplayName("회원 정보 수정 성공 - 주소가 null일 때")
    void updateMemberInfoSuccess2() throws Exception {
        // given
        // 회원 정보 수정
        MemberUpdate memberUpdate = MemberUpdate.builder()
            .nickname("Cha Cha")
            .build();

        // when
        // 회원 정보 수정
        mockMvc.perform(post("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(memberUpdate)))

        // then
        .andExpect(status().isNoContent()).andReturn();

        // 회원 정보 조회
        MvcResult result = mockMvc.perform(get("/api/members")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json"))
            .andExpect(status().isOk()).andReturn();

        String body = result.getResponse().getContentAsString(UTF_8);
        MemberResponse memberInfo = objectMapper.readValue(body, MemberResponse.class);

        assertThat(memberInfo.getNickname()).isEqualTo("Cha Cha");
        assertThat(memberInfo.getAddress()).isNull();
        assertThat(memberInfo.getGender()).isNull();
    }
}
