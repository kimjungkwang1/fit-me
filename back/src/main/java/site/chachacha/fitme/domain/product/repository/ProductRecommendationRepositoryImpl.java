package site.chachacha.fitme.domain.product.repository;

import static site.chachacha.fitme.domain.product.entity.QProductRecommendation.productRecommendation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import site.chachacha.fitme.domain.product.entity.ProductRecommendation;

@RequiredArgsConstructor
public class ProductRecommendationRepositoryImpl implements
    ProductRecommendationQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductRecommendation> findAllByIdWithProduct(Long productId) {
        return queryFactory.selectFrom(productRecommendation)
            .leftJoin(productRecommendation.product).fetchJoin()
            .where(productRecommendation.product.id.eq(productId))
            .fetch();
    }
}
