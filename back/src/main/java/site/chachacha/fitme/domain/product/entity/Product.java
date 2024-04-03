package site.chachacha.fitme.domain.product.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import site.chachacha.fitme.common.entity.BaseEntity;
import site.chachacha.fitme.domain.brand.entity.Brand;
import site.chachacha.fitme.domain.category.entity.Category;
import site.chachacha.fitme.domain.like.entity.ProductLike;
import site.chachacha.fitme.domain.order.entity.OrderProduct;
import site.chachacha.fitme.domain.review.entity.ProductReview;
import site.chachacha.fitme.domain.tag.entity.ProductTag;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "brand_id")
    @ManyToOne(fetch = LAZY)
    private Brand brand;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = LAZY)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProductLike> productLikes = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProductView> productViews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProductReview> productReviews = new ArrayList<>();

    @ColumnDefault("0")
    private int reviewCount = 0;

    @ColumnDefault("0.0")
    private double reviewRating = 0.0;

    @OneToMany(mappedBy = "product")
    private List<ProductTag> productTags;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;  // MALE = 0, FEMALE = 1, UNISEX = 2

    private String ageRange;

    private Integer price;

    private int likeCount = 0;

    private double monthlyPopularityScore = 0.0;

//    private int weeklyPopularityScore = 0;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MainImage> mainImage = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<DetailImage> detailImage = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Product(Brand brand, Category category, String name, Gender gender, String ageRange,
        Integer price) {
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.gender = gender;
        this.ageRange = ageRange;
        this.price = price;
    }

    // == 비즈니스 로직 == //
    public void addReview(ProductReview productReview, int rating) {
        productReviews.add(productReview);
        this.reviewRating = (this.reviewRating * this.reviewCount + rating) / (++this.reviewCount);
    }

    public void deleteReview(int rating) {
        this.reviewRating = (this.reviewRating * this.reviewCount - rating) / (--this.reviewCount);
    }

    public void addLike(ProductLike productLike) {
        this.productLikes.add(productLike);
        this.likeCount = this.likeCount + 1;
    }

    public void updateMonthlyPopularityScore(int monthlyPopularityScore) {
        this.monthlyPopularityScore = monthlyPopularityScore;
    }

    public void deleteLike() {
        this.likeCount = this.likeCount - 1;
    }

    // == 연관관계 메소드 == //
    public void addMainImage(MainImage mainImage) {
        this.mainImage.add(mainImage);
    }

    public void addDetailImage(DetailImage detailImage) {
        this.detailImage.add(detailImage);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }

    public void addProductOption(ProductOption productOption) {
        this.productOptions.add(productOption);
    }
}
