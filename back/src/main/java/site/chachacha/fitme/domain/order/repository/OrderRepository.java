package site.chachacha.fitme.domain.order.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.chachacha.fitme.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryQueryDsl {

    List<Order> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<Order> findByIdAndMemberId(Long orderId, Long memberId);
}
