package site.chachacha.fitme.dressroom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import site.chachacha.fitme.advice.exception.BadRequestException;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.domain.auth.service.JwtService;
import site.chachacha.fitme.domain.dressroom.dto.DressRoomResponse;
import site.chachacha.fitme.domain.dressroom.entity.Model;
import site.chachacha.fitme.domain.dressroom.repository.ModelRepository;
import site.chachacha.fitme.domain.dressroom.service.DressRoomService;
import site.chachacha.fitme.domain.dressroom.service.ModelService;

@TestInstance(PER_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
@TestMethodOrder(OrderAnnotation.class)
public class DressRoomTest {

    @Autowired
    private ModelService modelService;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private DressRoomService dressRoomService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtService jwtService;

    private String accessToken, refreshToken;

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
        String[] jwts = jwtService.issueJwts(1L);
        accessToken = jwts[0];
        refreshToken = jwts[1];
        jwtService.saveRefreshTokenToRedis(refreshToken, 1L);

        // Model 생성
        List<Model> models = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Model model = Model.builder()
                .url("Dummy Dummer URL" + i)
                .build();
            models.add(model);
        }

        // Model 저장
        modelRepository.saveAll(models);

        // Product 생성
        try (Connection connection = dataSource.getConnection()) {
            executeSqlScript(connection,
                new FileSystemResource("src/test/resources/sql/top.sql"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection connection = dataSource.getConnection()) {
            executeSqlScript(connection,
                new FileSystemResource("src/test/resources/sql/bottom.sql"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(100)
    @DisplayName("드레스룸 모델 조회 성공")
    void findModels() {
        // When
        List<Model> allModels = modelService.findAllModels();

        // Then
        assertThat(allModels.size()).isEqualTo(5);

        for (int i = 0; i < 5; i++) {
            assertThat(allModels.get(i).getUrl()).isEqualTo("Dummy Dummer URL" + (i + 1));
        }
    }

    @Test
    @Order(200)
    @DisplayName("드레스룸 생성 성공")
    void createDressRoom() {
        // Given
        DressRoomResponse dressRoom1 = dressRoomService.createDressRoom(1L, 1L, 1L, 1L);

        // When
        DressRoomResponse dressRoomResponse1 = dressRoomService.findByIdAndMemberId(1L,
            dressRoom1.getId());

        // Then
        assertThat(dressRoomResponse1.getId()).isEqualTo(1L);
        assertThat(dressRoomResponse1.getImageUrl()).isEqualTo(null);

        // Given
        DressRoomResponse dressRoom2 = dressRoomService.createDressRoom(1L, 1L, 1L, null);

        // When
        DressRoomResponse dressRoomResponse2 = dressRoomService.findByIdAndMemberId(1L,
            dressRoom2.getId());

        // Then
        assertThat(dressRoomResponse2.getId()).isEqualTo(2L);
        assertThat(dressRoomResponse2.getImageUrl()).isEqualTo(null);

        // Given
        DressRoomResponse dressRoom3 = dressRoomService.createDressRoom(1L, 1L, null, 1L);

        // When
        DressRoomResponse dressRoomResponse3 = dressRoomService.findByIdAndMemberId(1L,
            dressRoom3.getId());

        // Then
        assertThat(dressRoomResponse3.getId()).isEqualTo(3L);
        assertThat(dressRoomResponse3.getImageUrl()).isEqualTo(null);
    }

    @Test
    @Order(210)
    @DisplayName("드레스룸 생성 실패 - 상의나 하의 중 하나는 선택해야 합니다.")
    void createDressRoomFail1() {
        // Given
        assertThatThrownBy(() -> {
            dressRoomService.createDressRoom(1L, 1L, null, null);
        })
            .isInstanceOf(BadRequestException.class)
            .hasMessage("상의나 하의 중 하나는 선택해야 합니다.");
    }

    @Test
    @Order(220)
    @DisplayName("드레스룸 생성 실패 - 존재하지 않는 회원입니다.")
    void createDressRoomFail2() {
        // Given
        assertThatThrownBy(() -> {
            dressRoomService.createDressRoom(2L, 1L, 1L, 1L);
        })
            .isInstanceOf(GoneException.class)
            .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    @Order(221)
    @DisplayName("드레스룸 생성 실패 - 존재하지 않는 모델입니다.")
    void createDressRoomFail3() {
        // Given
        assertThatThrownBy(() -> {
            dressRoomService.createDressRoom(1L, 6L, 1L, 1L);
        })
            .isInstanceOf(GoneException.class)
            .hasMessage("존재하지 않는 모델입니다.");
    }

    @Test
    @Order(222)
    void createDressRoomFail4() {
        // Given
        assertThatThrownBy(() -> {
            dressRoomService.createDressRoom(1L, 1L, 11L, 1L);
        })
            .isInstanceOf(GoneException.class)
            .hasMessage("존재하지 않는 상의입니다.");
    }

    @Test
    @Order(223)
    void createDressRoomFail5() {
        // Given
        assertThatThrownBy(() -> {
            dressRoomService.createDressRoom(1L, 1L, 6L, 15L);
        })
            .isInstanceOf(GoneException.class)
            .hasMessage("존재하지 않는 하의입니다.");
    }

    @Test
    @Order(300)
    @DisplayName("드레스룸 목록 조회 성공")
    void findDressRoomList() {
        // When
        List<DressRoomResponse> dressRoomResponses = dressRoomService.findNoOffsetByMemberId(1L,
            null);

        // Then
        assertThat(dressRoomResponses.size()).isEqualTo(3);
    }

    @Test
    @Order(400)
    @DisplayName("드레스룸 조회 성공")
    void findDressRoom() {
        // When
        DressRoomResponse dressRoomResponse = dressRoomService.findByIdAndMemberId(1L, 1L);

        // Then
        assertThat(dressRoomResponse.getId()).isEqualTo(1L);
        assertThat(dressRoomResponse.getImageUrl()).isEqualTo(null);
    }

    @Test
    @Order(410)
    @DisplayName("드레스룸 조회 실패 - 해당 드레스룸이 존재하지 않습니다.")
    void findDressRoomFail() {
        // Given
        assertThatThrownBy(() -> {
            dressRoomService.findByIdAndMemberId(1L, 4L);
        })
            .isInstanceOf(GoneException.class)
            .hasMessage("해당 드레스룸이 존재하지 않습니다.");
    }

    @Test
    @Order(500)
    @DisplayName("드레스룸 삭제 성공")
    void deleteDressRoom() {
        // When
        dressRoomService.deleteDressRoom(1L, 2L);

        // Then
        assertThatThrownBy(() -> {
            dressRoomService.findByIdAndMemberId(1L, 2L);
        })
            .isInstanceOf(GoneException.class)
            .hasMessage("해당 드레스룸이 존재하지 않습니다.");
    }

    @Test
    @Order(600)
    @DisplayName("드레스룸 목록 조회 성공")
    void findDressRoomListAfterDelete() {
        // When
        List<DressRoomResponse> dressRoomResponses = dressRoomService.findNoOffsetByMemberId(1L,
            null);

        // Then
        assertThat(dressRoomResponses.size()).isEqualTo(2);
    }
}
