package site.chachacha.fitme.domain.product.repository;

import static site.chachacha.fitme.domain.product.entity.QProductSize.productSize;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import site.chachacha.fitme.domain.product.entity.ProductSize;

@RequiredArgsConstructor
public class ProductSizeRepositoryImpl implements ProductSizeQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ProductSize> findByIdAndProductOptionId(Long id, Long productOptionId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(productSize)
            .where(productSize.id.eq(id)
                .and(productSize.productOption.id.eq(productOptionId)))
            .fetchOne());
    }
}
