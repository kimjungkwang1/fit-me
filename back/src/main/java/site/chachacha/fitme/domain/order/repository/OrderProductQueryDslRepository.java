package site.chachacha.fitme.domain.order.repository;

import java.util.List;
import java.util.Optional;
import site.chachacha.fitme.domain.order.entity.OrderProduct;

public interface OrderProductQueryDslRepository {

    Optional<OrderProduct> findAllByMemberIdAndProductId(Long memberId, Long productId);

    Boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    List<OrderProduct> findAllByOrderIdWithProductSize(Long orderId);
}
