package site.chachacha.fitme.domain.review.dto;

import static lombok.AccessLevel.PROTECTED;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class ProductReviewRequest {

    @NotNull
    @Min(1) @Max(5)
    private Integer rating;

    @NotNull
    private String content;

    @Builder
    private ProductReviewRequest(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
    }
}
