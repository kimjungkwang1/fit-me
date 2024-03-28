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
public class ProductReviewResponseWithMemberNickname extends ProductReviewResponse {

    private String memberNickname;

    protected ProductReviewResponseWithMemberNickname(Long id, int rating, String content,
        String imageUrl, LocalDateTime createdAt, String memberNickname) {
        super(id, rating, content, imageUrl, createdAt);
        this.memberNickname = memberNickname;
    }

    public static ProductReviewResponseWithMemberNickname of(ProductReview productReview) {
        return ProductReviewResponseWithMemberNickname.builder()
            .id(productReview.getId())
            .rating(productReview.getRating())
            .content(productReview.getContent())
            .imageUrl(productReview.getImageUrl())
            .createdAt(productReview.getCreatedDate())
            .memberNickname(productReview.getMember().getNickname())
            .build();
    }
}
