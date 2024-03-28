package site.chachacha.fitme.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.domain.auth.service.JwtService;
import site.chachacha.fitme.domain.brand.entity.Brand;
import site.chachacha.fitme.domain.brand.repository.BrandRepository;
import site.chachacha.fitme.domain.category.entity.Category;
import site.chachacha.fitme.domain.category.repository.CategoryRepository;
import site.chachacha.fitme.domain.order.dto.OrderRequest;
import site.chachacha.fitme.domain.order.service.OrderService;
import site.chachacha.fitme.domain.product.entity.Gender;
import site.chachacha.fitme.domain.product.entity.MainImage;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;
import site.chachacha.fitme.domain.product.entity.ProductSize;
import site.chachacha.fitme.domain.product.repository.ProductOptionRepository;
import site.chachacha.fitme.domain.product.repository.ProductRepository;
import site.chachacha.fitme.domain.product.repository.ProductSizeRepository;
import site.chachacha.fitme.domain.review.dto.ProductReviewRequest;
import site.chachacha.fitme.domain.review.dto.ProductReviewResponseWithMemberNickname;
import site.chachacha.fitme.domain.review.exception.DuplicatedReviewException;
import site.chachacha.fitme.domain.review.repository.ProductReviewRepository;
import site.chachacha.fitme.domain.review.service.ProductReviewService;

@TestInstance(PER_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ProductReviewTest {

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
    private OrderService orderService;

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MockMvc mockMvc;

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
            .product(product2)
            .color("white")
            .build();

        // ProductSize 생성
        ProductSize productSize1 = ProductSize.builder()
            .productOption(productOption1)
            .size("S")
            .stockQuantity(10)
            .build();

        ProductSize productSize2 = ProductSize.builder()
            .productOption(productOption2)
            .size("M")
            .stockQuantity(10)
            .build();

        productRepository.save(product1);
        productRepository.save(product2);

        productOptionRepository.save(productOption1);
        productOptionRepository.save(productOption2);

        productSizeRepository.save(productSize1);
        productSizeRepository.save(productSize2);
    }

    @Test
    @Order(100)
    @DisplayName("상품 리뷰 생성 성공")
    void createProductReview() throws IOException {
        // given
        // OrderRequest 생성
        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(OrderRequest.builder()
            .productOptionId(1L)
            .productSizeId(1L)
            .count(1)
            .build());

        // Order 생성
        orderService.createOrder(1L, orderRequests);

        // when
        // 상품 리뷰 생성
        productReviewService.createReview(1L, 1L, ProductReviewRequest.builder()
                .rating(5)
                .content("좋아요")
                .build(),
            new MockMultipartFile("image", "image.jpg", "image/jpeg",
                new FileInputStream("src/test/resources/Cha Cha.jpg")));

        // then
        // 상품 리뷰 생성 확인
        ProductReviewResponseWithMemberNickname response = productReviewService.getReview(1L);

        assertThat(response.getRating()).isEqualTo(5);
        assertThat(response.getContent()).isEqualTo("좋아요");
        String[] split = response.getImageUrl().split("/");
        assertThat(split[split.length - 2]).isEqualTo("1");

        // count & rating 증가 확인
        // when
        Product product = productRepository.findById(1L)
            .orElseThrow(() -> new GoneException("존재하지 않는 상품입니다."));

        ProductSize productSize = productSizeRepository.findById(1L)
            .orElseThrow(() -> new GoneException("존재하지 않는 ProductSize입니다."));

        // then
        assertThat(productSize.getStockQuantity()).isEqualTo(9);
        assertThat(product.getReviewRating()).isEqualTo(5.0);
        assertThat(product.getReviewCount()).isEqualTo(1);

        // ToDo: 이미지 업로드 확인

    }

    @Test
    @Order(110)
    @DisplayName("상품 리뷰 생성 실패 - 존재하지 않는 회원")
    void createProductReviewWithNotExistMember() {
        // when & then
        // 상품 리뷰 생성 실패 확인
        assertThatThrownBy(() -> productReviewService.createReview(10L, 1L,
            ProductReviewRequest.builder()
                .rating(5)
                .content("좋아요")
                .build(),
            new MockMultipartFile("image", "image.jpg", "image/jpeg",
                new FileInputStream("src/test/resources/Cha Cha.jpg"))))
            .isInstanceOf(GoneException.class)
            .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    @Order(111)
    @DisplayName("상품 리뷰 생성 실패 - 구매 내역이 없음")
    void createProductReviewWithNotExistOrderProduct() {
        // when & then
        // 상품 리뷰 생성 실패 확인
        assertThatThrownBy(() -> productReviewService.createReview(1L, 2L,
            ProductReviewRequest.builder()
                .rating(5)
                .content("좋아요")
                .build(),
            new MockMultipartFile("image", "image.jpg", "image/jpeg",
                new FileInputStream("src/test/resources/Cha Cha.jpg"))))
            .isInstanceOf(GoneException.class)
            .hasMessage("구매 내역이 없습니다.");
    }

    @Test
    @Order(112)
    @DisplayName("상품 리뷰 생성 실패 - 상품 리뷰 이미 존재")
    void createProductReviewWithExistProductReview() throws Exception {
        // when & then
        // 상품 리뷰 생성 실패 확인
        assertThatThrownBy(() -> productReviewService.createReview(1L, 1L,
            ProductReviewRequest.builder()
                .rating(5)
                .content("좋아요")
                .build(),
            new MockMultipartFile("image", "image.jpg", "image/jpeg",
                new FileInputStream("src/test/resources/Cha Cha.jpg"))))
            .isInstanceOf(DuplicatedReviewException.class)
            .hasMessage("이미 리뷰를 작성하셨습니다.");
    }

    @Test
    @Order(113)
    @DisplayName("상품 리뷰 생성 실패 - 잘못된 상품 id")
    void createProductReviewWithNotExistProduct() {
        // when & then
        // 상품 리뷰 생성 실패 확인
        assertThatThrownBy(() -> productReviewService.createReview(1L, 10L,
            ProductReviewRequest.builder()
                .rating(5)
                .content("좋아요")
                .build(),
            new MockMultipartFile("image", "image.jpg", "image/jpeg",
                new FileInputStream("src/test/resources/Cha Cha.jpg"))))
            .isInstanceOf(GoneException.class)
            .hasMessage("구매 내역이 없습니다.");
    }

    @Test
    @Order(120)
    @DisplayName("상품 리뷰 생성 실패 - rating 없음")
    void createProductReviewWithoutRating() throws Exception {
        MvcResult mvcResult = mockMvc.perform(multipart("/api/products/1/reviews")
                .file(new MockMultipartFile("image", "image.jpg", "image/jpeg",
                    new FileInputStream("src/test/resources/Cha Cha.jpg")))
                .file(new MockMultipartFile("productReviewRequest", "productReviewRequest",
                    "application/json", "{\"content\": \"좋아요\"}".getBytes()))
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isBadRequest()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains(
            "'rating': must not be null");
    }

    @Test
    @Order(121)
    @DisplayName("상품 리뷰 생성 실패 - rating 0 이하")
    void createProductReviewWithInvalidRating() throws Exception {
        MvcResult mvcResult = mockMvc.perform(multipart("/api/products/1/reviews")
                .file(new MockMultipartFile("image", "image.jpg", "image/jpeg",
                    new FileInputStream("src/test/resources/Cha Cha.jpg")))
                .file(new MockMultipartFile("productReviewRequest", "productReviewRequest",
                    "application/json", "{\"rating\": 0, \"content\": \"좋아요\"}".getBytes()))
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isBadRequest()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains(
            "'rating': must be greater than or equal to 1");
    }

    @Test
    @Order(122)
    @DisplayName("상품 리뷰 생성 실패 - rating 5 초과")
    void createProductReviewWithInvalidRating2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(multipart("/api/products/1/reviews")
                .file(new MockMultipartFile("image", "image.jpg", "image/jpeg",
                    new FileInputStream("src/test/resources/Cha Cha.jpg")))
                .file(new MockMultipartFile("productReviewRequest", "productReviewRequest",
                    "application/json", "{\"rating\": 6, \"content\": \"좋아요\"}".getBytes()))
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isBadRequest()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains(
            "'rating': must be less than or equal to 5");
    }

    @Test
    @Order(123)
    @DisplayName("상품 리뷰 생성 실패 - content 없음")
    void createProductReviewWithoutContent() throws Exception {
        MvcResult mvcResult = mockMvc.perform(multipart("/api/products/1/reviews")
                .file(new MockMultipartFile("image", "image.jpg", "image/jpeg",
                    new FileInputStream("src/test/resources/Cha Cha.jpg")))
                .file(new MockMultipartFile("productReviewRequest", "productReviewRequest",
                    "application/json", "{\"rating\": 5}".getBytes()))
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isBadRequest()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains(
            "'content': must not be null");
    }

//    // ToDo: 이미지 업로드 실패 테스트
//    @Test
//    @Order(130)
//    @DisplayName("상품 리뷰 생성 실패 - 이미지 없음")
//
//    @Test
//    @Order(131)
//    @DisplayName("상품 리뷰 생성 실패 - 이미지 크기 초과")
//
//    @Test
//    @Order(132)
//    @DisplayName("상품 리뷰 생성 실패 - 이미지 확장자 지원하지 않음")
}
