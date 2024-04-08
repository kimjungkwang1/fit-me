package site.fitme.batch.domain.product.entity;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import site.fitme.batch.domain.brand.entity.Brand;
import site.fitme.batch.entity.common.BaseEntity;

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

    @ColumnDefault("0")
    private int reviewCount = 0;

    @ColumnDefault("0.0")
    private double reviewRating = 0.0;

    private String name;

    private String ageRange;

    private Integer price;

    private int likeCount = 0;

    private double monthlyPopularityScore = 0.0;

    @OneToMany(mappedBy = "product", cascade = PERSIST, orphanRemoval = true)
    private List<MainImage> mainImage = new ArrayList<>();

    public double getReviewRating() {
        // 소수 둘째자리에서 반올림
        return Math.round(this.reviewRating * 10) / 10.0;
    }

    // == 비즈니스 로직 == //

    public void updateMonthlyPopularityScore(double monthlyPopularityScore) {
        this.monthlyPopularityScore = monthlyPopularityScore;
    }

    public void deleteLike() {
        this.likeCount = this.likeCount - 1;
    }

    // == 연관관계 메소드 == //
    public void addMainImage(MainImage mainImage) {
        this.mainImage.add(mainImage);
    }
}
