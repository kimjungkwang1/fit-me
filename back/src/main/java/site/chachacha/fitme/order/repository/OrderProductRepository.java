package site.chachacha.fitme.order.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.domain.order.OrderProduct;
import site.chachacha.fitme.domain.product.repository.support.ProductScore;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT op.product.id AS productId, SUM(op.count) * 8 AS score " +
        "FROM OrderProduct op " +
        "WHERE op.createdDate > :since " +
        "GROUP BY op.product.id")
    List<ProductScore> findScoresForOrderProductsSince(@Param("since") LocalDateTime since);
}
