package site.fitme.batch.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.fitme.batch.domain.like.entity.ProductLike;
import site.fitme.batch.repository.support.ProductScore;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    @Query("SELECT pl.product.id AS productId, COUNT(pl) * 2 AS score " +
        "FROM ProductLike pl " +
        "WHERE pl.createdDate > :since " +
        "GROUP BY pl.product.id")
    List<ProductScore> findScoresForProductLikesSince(@Param("since") LocalDateTime since);
}
