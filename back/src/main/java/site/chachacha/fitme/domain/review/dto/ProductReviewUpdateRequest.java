package site.chachacha.fitme.domain.review.dto;

import static lombok.AccessLevel.PROTECTED;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class ProductReviewUpdateRequest {

    @NotNull
    private String content;
}
