package site.chachacha.fitme.domain.product.repository;

import static site.chachacha.fitme.domain.product.entity.QProductOption.productOption;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import site.chachacha.fitme.domain.product.entity.ProductOption;

@RequiredArgsConstructor
public class ProductOptionRepositoryImpl implements ProductOptionQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ProductOption> findByIdWithProduct(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(productOption)
            .where(productOption.id.eq(id))
            .leftJoin(productOption.product).fetchJoin()
            .fetchOne());
    }
}
