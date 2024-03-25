package site.chachacha.fitme.domain.cart.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CartListResponse {

    private List<CartResponse> carts;
    private int totalProductCount;
    private int totalCartPrice;

    public CartListResponse(List<CartResponse> carts, int totalProductCount, int totalCartPrice) {
        this.carts = carts;
        this.totalProductCount = totalProductCount;
        this.totalCartPrice = totalCartPrice;
    }
}
