package site.chachacha.fitme.domain.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartUpdateRequest {

    @Min(value = 1, message = "장바구니 ID는 1 이상이여야 합니다.")
    @NotNull(message = "장바구니 ID는 필수 항목입니다.")
    private Long cartId;

    @Min(value = 1, message = "수량은 1 이상이여야 합니다.")
    @NotNull(message = "수량은 필수 항목입니다.")
    private int quantity;
}
