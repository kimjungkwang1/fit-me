package site.chachacha.fitme.domain.review.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.common.entity.BaseEntity;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.product.entity.Product;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class ProductReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int rating;

    @NotNull
    private String content = "";

    @NotBlank
    private String imageUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private ProductReview(int rating, String content, String imageUrl, Member member,
        Product product) {
        this.rating = rating;
        this.content = content;
        this.imageUrl = imageUrl;
        this.member = member;
        this.member.addProductReview(this);

        this.product = product;
        // count & rating 증가
        this.product.addReview(this, rating);
    }

    // == 비즈니스 로직 == //
    public void updateReview(String content) {
        this.content = content;
    }

    public void updateImageUrl(Long id) {
        this.imageUrl += id + "/" + "image.jpg";
    }
}
