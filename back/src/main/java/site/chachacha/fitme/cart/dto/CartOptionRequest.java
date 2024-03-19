package site.chachacha.fitme.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartOptionRequest {

    @Min(value = 1, message = "상품 옵션 ID는 1 이상이여야 합니다.")
    @NotNull(message = "상품 옵션 ID는 필수 항목입니다.")
    private Long productOptionId;

    @Min(value = 1, message = "수량은 1 이상이여야 합니다.")
    @NotNull(message = "수량은 필수 항목입니다.")
    private int quantity;
}
