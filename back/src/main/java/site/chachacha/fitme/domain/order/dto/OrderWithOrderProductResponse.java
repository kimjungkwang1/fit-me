package site.chachacha.fitme.domain.order.dto;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.domain.order.entity.Order;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderWithOrderProductResponse {

    private Long id;

    private String status;

    private List<OrderProductResponse> orderProducts;

    @Builder
    private OrderWithOrderProductResponse(Long id, String status, Order order) {
        this.id = id;
        this.status = status;
        this.orderProducts = OrderProductResponse.of(order);
    }

    public static List<OrderWithOrderProductResponse> of(List<Order> orders) {
        return orders.stream()
            .map(order -> OrderWithOrderProductResponse.builder()
                .id(order.getId())
                .status(order.getStatus().name())
                .order(order)
                .build())
            .toList();
    }
}
