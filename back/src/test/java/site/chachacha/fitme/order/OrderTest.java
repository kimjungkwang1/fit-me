package site.chachacha.fitme.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;

import java.io.IOException;
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
import org.springframework.data.domain.PageRequest;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.domain.auth.service.JwtService;
import site.chachacha.fitme.domain.brand.entity.Brand;
import site.chachacha.fitme.domain.brand.repository.BrandRepository;
import site.chachacha.fitme.domain.category.entity.Category;
import site.chachacha.fitme.domain.category.repository.CategoryRepository;
import site.chachacha.fitme.domain.order.OrderStatus;
import site.chachacha.fitme.domain.order.dto.OrderProductResponse;
import site.chachacha.fitme.domain.order.dto.OrderRequest;
import site.chachacha.fitme.domain.order.dto.OrderWithOrderProductResponse;
import site.chachacha.fitme.domain.order.service.OrderService;
import site.chachacha.fitme.domain.product.dto.ProductResponseForOrder;
import site.chachacha.fitme.domain.product.entity.Gender;
import site.chachacha.fitme.domain.product.entity.MainImage;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;
import site.chachacha.fitme.domain.product.entity.ProductSize;
import site.chachacha.fitme.domain.product.repository.ProductOptionRepository;
import site.chachacha.fitme.domain.product.repository.ProductRepository;
import site.chachacha.fitme.domain.product.repository.ProductSizeRepository;
import site.chachacha.fitme.domain.review.repository.ProductReviewRepository;

@TestInstance(PER_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
@TestMethodOrder(OrderAnnotation.class)
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtService jwtService;

    private String accessToken, refreshToken;

    @BeforeAll
    void setUp() throws IOException {
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

        // Brand
        Brand brand = Brand.builder()
            .name("brand")
            .build();
        brandRepository.save(brand);

        // Category
        Category category = Category.builder()
            .name("category")
            .build();
        categoryRepository.save(category);

        // Product 생성
        Product product1 = Product.builder()
            .brand(brand)
            .category(category)
            .name("product1")
            .gender(Gender.MALE)
            .ageRange("20대")
            .price(10000)
            .build();

        Product product2 = Product.builder()
            .brand(brand)
            .category(category)
            .name("product2")
            .gender(Gender.FEMALE)
            .ageRange("30대")
            .price(20000)
            .build();

        // MainImage 생성
        MainImage mainImage1 = MainImage.builder()
            .url("mainImage1")
            .product(product1)
            .build();

        MainImage mainImage2 = MainImage.builder()
            .url("mainImage2")
            .product(product2)
            .build();

        // ProductOption 생성
        ProductOption productOption1 = ProductOption.builder()
            .product(product1)
            .color("black")
            .build();

        ProductOption productOption2 = ProductOption.builder()
            .product(product1)
            .color("red")
            .build();

        ProductOption productOption3 = ProductOption.builder()
            .product(product2)
            .color("white")
            .build();

        ProductOption productOption4 = ProductOption.builder()
            .product(product2)
            .color("blue")
            .build();

        // ProductSize 생성
        ProductSize productSize1 = ProductSize.builder()
            .productOption(productOption1)
            .size("S")
            .stockQuantity(10)
            .build();

        ProductSize productSize2 = ProductSize.builder()
            .productOption(productOption1)
            .size("M")
            .stockQuantity(10)
            .build();

        ProductSize productSize3 = ProductSize.builder()
            .productOption(productOption2)
            .size("S")
            .stockQuantity(10)
            .build();

        ProductSize productSize4 = ProductSize.builder()
            .productOption(productOption2)
            .size("M")
            .stockQuantity(10)
            .build();

        ProductSize productSize5 = ProductSize.builder()
            .productOption(productOption3)
            .size("S")
            .stockQuantity(10)
            .build();

        ProductSize productSize6 = ProductSize.builder()
            .productOption(productOption3)
            .size("M")
            .stockQuantity(10)
            .build();

        ProductSize productSize7 = ProductSize.builder()
            .productOption(productOption4)
            .size("S")
            .stockQuantity(10)
            .build();

        ProductSize productSize8 = ProductSize.builder()
            .productOption(productOption4)
            .size("M")
            .stockQuantity(10)
            .build();

        productRepository.save(product1);
        productRepository.save(product2);

        productOptionRepository.save(productOption1);
        productOptionRepository.save(productOption2);
        productOptionRepository.save(productOption3);
        productOptionRepository.save(productOption4);

        productSizeRepository.save(productSize1);
        productSizeRepository.save(productSize2);
        productSizeRepository.save(productSize3);
        productSizeRepository.save(productSize4);
        productSizeRepository.save(productSize5);
        productSizeRepository.save(productSize6);
        productSizeRepository.save(productSize7);
        productSizeRepository.save(productSize8);
    }

    // 주문 생성 테스트
    @Test
    @Order(100)
    @DisplayName("주문 생성 테스트 성공")
    void createOrderTest1() {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(1L)
            .productSizeId(1L)
            .count(1)
            .build());

        // when
        // Order 생성
        orderService.createOrder(1L, orderRequests);

        // then
        // 주문 생성 확인
        List<OrderWithOrderProductResponse> orderList = orderService.getOrderList(1L,
            PageRequest.of(0, 10));
        assertThat(orderList.size()).isEqualTo(1);

        OrderWithOrderProductResponse order = orderList.get(0);
        assertThat(order.getStatus().equals(OrderStatus.ORDERED.name()));

        OrderProductResponse orderProduct = order.getOrderProducts().get(0);
        assertThat(orderProduct.getColor()).isEqualTo("black");
        assertThat(orderProduct.getSize()).isEqualTo("S");
        assertThat(orderProduct.getPrice()).isEqualTo(10000);
        assertThat(orderProduct.getCount()).isEqualTo(1);

        ProductResponseForOrder product = orderProduct.getProduct();

        assertThat(product.getBrandName()).isEqualTo("brand");
        assertThat(product.getCategoryId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("product1");
        assertThat(product.getUrl()).isEqualTo(
            "https://fit-me.site/images/products/1/main/mainimage_1_1.jpg");

        // 재고 줄어드는지 확인할 것
        ProductSize productSize = productSizeRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ProductSize입니다."));

        assertThat(productSize.getStockQuantity()).isEqualTo(9);
    }

    @Test
    @Order(101)
    @DisplayName("주문 생성 테스트 성공 - 똑같은 상품 똑같은 사이즈 여러개 주문")
    void createOrderTest2() {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(1L)
            .productSizeId(1L)
            .count(1)
            .build());

        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(1L)
            .productSizeId(1L)
            .count(1)
            .build());

        // when
        // Order 생성
        orderService.createOrder(1L, orderRequests);

        // then
        // 주문 생성 확인
        List<OrderWithOrderProductResponse> orderList = orderService.getOrderList(1L,
            PageRequest.of(0, 10));
        assertThat(orderList.size()).isEqualTo(2);

        OrderWithOrderProductResponse order = orderList.get(1);
        assertThat(order.getStatus().equals(OrderStatus.ORDERED.name()));

        OrderProductResponse orderProduct = order.getOrderProducts().get(0);
        assertThat(orderProduct.getColor()).isEqualTo("black");
        assertThat(orderProduct.getSize()).isEqualTo("S");
        assertThat(orderProduct.getPrice()).isEqualTo(10000);
        assertThat(orderProduct.getCount()).isEqualTo(1);

        // 주문 상품이 2개인지 확인
        assertThat(order.getOrderProducts().size()).isEqualTo(2);

        ProductResponseForOrder product = orderProduct.getProduct();

        assertThat(product.getBrandName()).isEqualTo("brand");
        assertThat(product.getCategoryId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("product1");
        assertThat(product.getUrl()).isEqualTo(
            "https://fit-me.site/images/products/1/main/mainimage_1_1.jpg");

        // 재고 줄어드는지 확인할 것
        ProductSize productSize = productSizeRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ProductSize입니다."));

        assertThat(productSize.getStockQuantity()).isEqualTo(7);
    }

    @Test
    @Order(102)
    @DisplayName("주문 생성 테스트 성공 - 똑같은 상품 다른 사이즈 여러개 주문")
    void createOrderTest3() {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(1L)
            .productSizeId(1L)
            .count(1)
            .build());

        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(1L)
            .productSizeId(2L)
            .count(1)
            .build());

        // when
        // Order 생성
        orderService.createOrder(1L, orderRequests);

        // then
        // 주문 생성 확인
        List<OrderWithOrderProductResponse> orderList = orderService.getOrderList(1L,
            PageRequest.of(0, 10));
        assertThat(orderList.size()).isEqualTo(3);

        OrderWithOrderProductResponse order = orderList.get(2);
        assertThat(order.getStatus().equals(OrderStatus.ORDERED.name()));

        OrderProductResponse orderProduct = order.getOrderProducts().get(0);
        assertThat(orderProduct.getColor()).isEqualTo("black");
        assertThat(orderProduct.getSize()).isEqualTo("S");
        assertThat(orderProduct.getPrice()).isEqualTo(10000);
        assertThat(orderProduct.getCount()).isEqualTo(1);

        // 주문 상품이 2개인지 확인
        assertThat(order.getOrderProducts().size()).isEqualTo(2);

        ProductResponseForOrder product = orderProduct.getProduct();

        assertThat(product.getBrandName()).isEqualTo("brand");
        assertThat(product.getCategoryId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("product1");
        assertThat(product.getUrl()).isEqualTo(
            "https://fit-me.site/images/products/1/main/mainimage_1_1.jpg");

        // 재고 줄어드는지 확인할 것
        ProductSize productSize1 = productSizeRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ProductSize입니다."));

        assertThat(productSize1.getStockQuantity()).isEqualTo(6);

        ProductSize productSize2 = productSizeRepository.findById(2L)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ProductSize입니다."));

        assertThat(productSize2.getStockQuantity()).isEqualTo(9);
    }

    @Test
    @Order(103)
    @DisplayName("주문 생성 테스트 성공 - 똑같은 상품 다른 옵션 다른 사이즈 여러개 주문")
    void createOrderTest4() {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(1L)
            .productSizeId(1L)
            .count(1)
            .build());

        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(2L)
            .productSizeId(3L)
            .count(1)
            .build());

        // when
        // Order 생성
        orderService.createOrder(1L, orderRequests);

        // then
        // 주문 생성 확인
        List<OrderWithOrderProductResponse> orderList = orderService.getOrderList(1L,
            PageRequest.of(0, 10));
        assertThat(orderList.size()).isEqualTo(4);

        OrderWithOrderProductResponse order = orderList.get(3);
        assertThat(order.getStatus().equals(OrderStatus.ORDERED.name()));

        OrderProductResponse orderProduct = order.getOrderProducts().get(0);
        assertThat(orderProduct.getColor()).isEqualTo("black");
        assertThat(orderProduct.getSize()).isEqualTo("S");
        assertThat(orderProduct.getPrice()).isEqualTo(10000);
        assertThat(orderProduct.getCount()).isEqualTo(1);

        // 주문 상품이 2개인지 확인
        assertThat(order.getOrderProducts().size()).isEqualTo(2);

        ProductResponseForOrder product = orderProduct.getProduct();

        assertThat(product.getBrandName()).isEqualTo("brand");
        assertThat(product.getCategoryId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("product1");
        assertThat(product.getUrl()).isEqualTo(
            "https://fit-me.site/images/products/1/main/mainimage_1_1.jpg");

        // 재고 줄어드는지 확인할 것
        ProductSize productSize1 = productSizeRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ProductSize입니다."));

        assertThat(productSize1.getStockQuantity()).isEqualTo(5);

        ProductSize productSize2 = productSizeRepository.findById(3L)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ProductSize입니다."));

        assertThat(productSize2.getStockQuantity()).isEqualTo(9);
    }

    @Test
    @Order(104)
    @DisplayName("주문 생성 테스트 성공 - 다른 상품 다른 옵션 다른 사이즈 여러개 주문")
    void createOrderTest5() {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(1L)
            .productSizeId(1L)
            .count(1)
            .build());

        orderRequests.add(OrderRequest.builder()
            .productId(2L)
            .productOptionId(3L)
            .productSizeId(5L)
            .count(1)
            .build());

        // when
        // Order 생성
        orderService.createOrder(1L, orderRequests);

        // then
        // 주문 생성 확인
        List<OrderWithOrderProductResponse> orderList = orderService.getOrderList(1L,
            PageRequest.of(0, 10));
        assertThat(orderList.size()).isEqualTo(5);

        OrderWithOrderProductResponse order = orderList.get(4);
        assertThat(order.getStatus().equals(OrderStatus.ORDERED.name()));

        OrderProductResponse orderProduct = order.getOrderProducts().get(0);
        assertThat(orderProduct.getColor()).isEqualTo("black");
        assertThat(orderProduct.getSize()).isEqualTo("S");
        assertThat(orderProduct.getPrice()).isEqualTo(10000);
        assertThat(orderProduct.getCount()).isEqualTo(1);

        // 주문 상품이 2개인지 확인
        assertThat(order.getOrderProducts().size()).isEqualTo(2);

        ProductResponseForOrder product = orderProduct.getProduct();

        assertThat(product.getBrandName()).isEqualTo("brand");
        assertThat(product.getCategoryId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("product1");
        assertThat(product.getUrl()).isEqualTo(
            "https://fit-me.site/images/products/1/main/mainimage_1_1.jpg");

        // 재고 줄어드는지 확인할 것
        ProductSize productSize1 = productSizeRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ProductSize입니다."));

        assertThat(productSize1.getStockQuantity()).isEqualTo(4);

        ProductSize productSize2 = productSizeRepository.findById(5L)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ProductSize입니다."));

        assertThat(productSize2.getStockQuantity()).isEqualTo(9);
    }

    @Test
    @Order(120)
    @DisplayName("주문 생성 테스트 실패 - 존재하지 않는 회원")
    void createOrderFailTest1() {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(1L)
            .productSizeId(1L)
            .count(1)
            .build());

        // when & then
        // Order 생성
        // 존재하지 않는 회원으로 주문 생성 시도
        assertThatThrownBy(() -> orderService.createOrder(2L, orderRequests))
            .isInstanceOf(GoneException.class)
            .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    @Order(121)
    @DisplayName("주문 생성 테스트 실패 - 존재하지 않는 ProductOption")
    void createOrderFailTest2() {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(5L)
            .productSizeId(1L)
            .count(1)
            .build());

        // when & then
        // Order 생성
        // 존재하지 않는 ProductOption으로 주문 생성 시도
        assertThatThrownBy(() -> orderService.createOrder(1L, orderRequests))
            .isInstanceOf(GoneException.class)
            .hasMessage("존재하지 않는 ProductOption입니다.");
    }

    @Test
    @Order(122)
    @DisplayName("주문 생성 테스트 실패 - ProductOption과 연관되지 않은 ProductSize")
    void createOrderFailTest3() {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productId(1L)
            .productOptionId(1L)
            .productSizeId(5L)
            .count(1)
            .build());

        // when & then
        // Order 생성
        // ProductOption과 연관되지 않은 ProductSize로 주문 생성 시도
        assertThatThrownBy(() -> orderService.createOrder(1L, orderRequests))
            .isInstanceOf(GoneException.class)
            .hasMessage("존재하지 않는 ProductSize입니다.");
    }

    @Test
    @Order(123)
    @DisplayName("주문 생성 테스트 실패 - 존재하지 않는 Product")
    void createOrderFailTest4() {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productId(3L)
            .productOptionId(1L)
            .productSizeId(1L)
            .count(1)
            .build());

        // when & then
        // Order 생성
        // 존재하지 않는 Product으로 주문 생성 시도
        assertThatThrownBy(() -> orderService.createOrder(1L, orderRequests))
            .isInstanceOf(GoneException.class)
            .hasMessage("존재하지 않는 Product입니다.");
    }

    // ToDo: 장바구니에 해당 상품이 있을 때, 주문 생성 시 장바구니에서 해당 상품 삭제되는지 확인할 것

    // 주문 조회
    @Test
    @Order(200)
    @DisplayName("주문 조회 테스트 성공")
    void getOrderListTest1() {
        // given
        // when
        // 주문 조회
        List<OrderWithOrderProductResponse> orderList = orderService.getOrderList(1L,
            PageRequest.of(0, 10));

        // then
        // 주문 조회 확인
        assertThat(orderList.size()).isEqualTo(5);

        // 첫번째 주문
        OrderWithOrderProductResponse order1 = orderList.get(0);
        assertThat(order1.getStatus().equals(OrderStatus.ORDERED.name()));

        OrderProductResponse order1product1 = order1.getOrderProducts().get(0);
        assertThat(order1product1.getColor()).isEqualTo("black");
        assertThat(order1product1.getSize()).isEqualTo("S");
        assertThat(order1product1.getPrice()).isEqualTo(10000);
        assertThat(order1product1.getCount()).isEqualTo(1);

        ProductResponseForOrder product1 = order1product1.getProduct();
        assertThat(product1.getBrandName()).isEqualTo("brand");
        assertThat(product1.getCategoryId()).isEqualTo(1L);
        assertThat(product1.getName()).isEqualTo("product1");
        assertThat(product1.getUrl()).isEqualTo(
            "https://fit-me.site/images/products/1/main/mainimage_1_1.jpg");

        // 세번째 주문
        OrderWithOrderProductResponse order3 = orderList.get(2);
        assertThat(order3.getStatus().equals(OrderStatus.ORDERED.name()));

        OrderProductResponse order3product2 = order3.getOrderProducts().get(1);
        assertThat(order3product2.getColor()).isEqualTo("black");
        assertThat(order3product2.getSize()).isEqualTo("M");
        assertThat(order3product2.getPrice()).isEqualTo(10000);
        assertThat(order3product2.getCount()).isEqualTo(1);
        assertThat(order3product2.getProduct().getName()).isEqualTo("product1");
        assertThat(order3product2.getProduct().getUrl()).isEqualTo(
            "https://fit-me.site/images/products/1/main/mainimage_1_1.jpg");

        // 네번째 주문
        OrderWithOrderProductResponse order4 = orderList.get(3);
        assertThat(order4.getStatus().equals(OrderStatus.ORDERED.name()));

        OrderProductResponse order4product2 = order4.getOrderProducts().get(1);
        assertThat(order4product2.getColor()).isEqualTo("red");
        assertThat(order4product2.getSize()).isEqualTo("S");
        assertThat(order4product2.getPrice()).isEqualTo(10000);
        assertThat(order4product2.getCount()).isEqualTo(1);
        assertThat(order4product2.getProduct().getName()).isEqualTo("product1");
        assertThat(order4product2.getProduct().getUrl()).isEqualTo(
            "https://fit-me.site/images/products/1/main/mainimage_1_1.jpg");
    }

    // 주문 취소
    @Test
    @Order(300)
    @DisplayName("주문 취소 테스트 성공")
    void cancelOrderTest1() {
        // given
        // when
        // 주문 취소
        orderService.cancelOrder(1L, 1L);

        // then
        // 주문 취소 확인
        List<OrderWithOrderProductResponse> orderList = orderService.getOrderList(1L,
            PageRequest.of(0, 10));
        assertThat(orderList.size()).isEqualTo(5);

        OrderWithOrderProductResponse order = orderList.get(0);
        assertThat(order.getStatus().equals(OrderStatus.CANCEL.name()));

        OrderProductResponse orderProduct = order.getOrderProducts().get(0);
        assertThat(orderProduct.getColor()).isEqualTo("black");
        assertThat(orderProduct.getSize()).isEqualTo("S");
        assertThat(orderProduct.getPrice()).isEqualTo(10000);
        assertThat(orderProduct.getCount()).isEqualTo(1);

        ProductResponseForOrder product = orderProduct.getProduct();

        assertThat(product.getBrandName()).isEqualTo("brand");
        assertThat(product.getCategoryId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("product1");
        assertThat(product.getUrl()).isEqualTo(
            "https://fit-me.site/images/products/1/main/mainimage_1_1.jpg");

        // 재고 증가하는지 확인할 것
        ProductSize productSize = productSizeRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ProductSize입니다."));

        assertThat(productSize.getStockQuantity()).isEqualTo(5);
    }

    // 주문 조회
}
