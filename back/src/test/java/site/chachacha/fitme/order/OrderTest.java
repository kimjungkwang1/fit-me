package site.chachacha.fitme.order;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(PER_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
@TestMethodOrder(OrderAnnotation.class)
public class OrderTest {
//    // TODO: 주문 생성 테스트
//    @Test
//    @Order(100)
//    @DisplayName("주문 생성 테스트 성공")
//    // ToDo: 재고 줄어드는지 확인할 것
}
