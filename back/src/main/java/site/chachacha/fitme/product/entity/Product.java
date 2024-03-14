package site.chachacha.fitme.product.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.brand.entity.Brand;
import site.chachacha.fitme.category.entity.Category;
import site.chachacha.fitme.entity.BaseEntity;
import site.chachacha.fitme.like.entity.ProductLike;
import site.chachacha.fitme.review.entity.ProductReview;
import site.chachacha.fitme.tag.entity.ProductTag;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "brand_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProductLike> productLikes = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductTag> productTags = new ArrayList<>();

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;  // MALE = 0, FEMALE = 1, UNISEX = 2

    private String ageRange;

    private Integer price;

    private int likeCount = 0;

    private String mainImageUrl;

    private String detailImageUrl;

    @Builder
    public Product(Brand brand, Category category, String name, Gender gender, String ageRange, Integer price, String mainImageUrl,
        String detailImageUrl) {
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.gender = gender;
        this.ageRange = ageRange;
        this.price = price;
        this.mainImageUrl = mainImageUrl;
        this.detailImageUrl = detailImageUrl;
    }
}
