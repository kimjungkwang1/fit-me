package site.chachacha.fitme.domain.order.dto;

import static lombok.AccessLevel.PROTECTED;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long productOptionId;

    @NotNull
    private Long productSizeId;

    @NotNull
    private int count;

    @Builder
    private OrderRequest(Long productId, Long productOptionId, Long productSizeId, int count) {
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.productSizeId = productSizeId;
        this.count = count;
    }
}
