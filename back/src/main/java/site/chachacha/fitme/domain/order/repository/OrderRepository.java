package site.chachacha.fitme.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.chachacha.fitme.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
