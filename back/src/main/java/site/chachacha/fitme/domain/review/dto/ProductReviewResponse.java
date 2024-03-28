package site.chachacha.fitme.domain.review.dto;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import site.chachacha.fitme.domain.review.entity.ProductReview;

@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
public class ProductReviewResponse {

    private Long id;
    private int rating;
    private String content;
    private String imageUrl;
    private String memberNickname;
    private LocalDateTime createdAt;

    protected ProductReviewResponse(Long id, int rating, String content, String imageUrl,
        LocalDateTime createdAt) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public static ProductReviewResponse of(ProductReview productReview) {
        return ProductReviewResponse.builder()
            .id(productReview.getId())
            .rating(productReview.getRating())
            .content(productReview.getContent())
            .imageUrl(productReview.getImageUrl())
            .createdAt(productReview.getCreatedDate())
            .build();
    }
}
