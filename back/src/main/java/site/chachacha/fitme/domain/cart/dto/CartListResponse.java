package site.chachacha.fitme.domain.cart.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CartListResponse {

    private List<CartResponse> carts;
    private int totalProductCount;

    public CartListResponse(List<CartResponse> carts, int totalProductCount) {
        this.carts = carts;
        this.totalProductCount = totalProductCount;
    }
}
