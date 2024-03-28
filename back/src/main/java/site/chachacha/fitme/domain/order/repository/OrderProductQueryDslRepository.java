package site.chachacha.fitme.domain.order.repository;

import java.util.Optional;
import site.chachacha.fitme.domain.order.OrderProduct;

public interface OrderProductQueryDslRepository {

    Optional<OrderProduct> findAllByMemberIdAndProductId(Long memberId, Long productId);

    Boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
