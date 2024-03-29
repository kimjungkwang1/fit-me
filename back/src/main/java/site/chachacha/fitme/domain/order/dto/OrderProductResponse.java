package site.chachacha.fitme.domain.order.dto;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.domain.order.entity.Order;
import site.chachacha.fitme.domain.order.entity.OrderProduct;
import site.chachacha.fitme.domain.product.dto.ProductResponseForOrder;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderProductResponse {

    private ProductResponseForOrder product;

    private String color;

    private String size;

    private int price;

    private int count;

    @Builder
    private OrderProductResponse(OrderProduct orderProduct) {
        this.product = ProductResponseForOrder.of(orderProduct);
        this.color = orderProduct.getColor();
        this.size = orderProduct.getSize();
        this.price = orderProduct.getPrice();
        this.count = orderProduct.getCount();
    }

    public static List<OrderProductResponse> of(Order order) {
        return order.getOrderProducts().stream()
            .map(
                orderProduct -> OrderProductResponse.builder()
                    .orderProduct(orderProduct)
                    .build()
            )
            .toList();
    }
}
