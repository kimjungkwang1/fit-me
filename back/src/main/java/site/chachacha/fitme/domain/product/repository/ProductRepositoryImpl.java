package site.chachacha.fitme.domain.product.repository;

import static site.chachacha.fitme.domain.product.entity.QProduct.product;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import site.chachacha.fitme.domain.product.entity.Product;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Product> findByIdWithBrand(Long productId) {
        return Optional.ofNullable(queryFactory.selectFrom(product)
            .leftJoin(product.brand).fetchJoin()
            .where(product.id.eq(productId))
            .fetchOne());
    }
}
