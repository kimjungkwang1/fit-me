package site.chachacha.fitme.domain.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductOptionRepositoryImpl implements ProductOptionQueryDslRepository {

    private final JPAQueryFactory queryFactory;
}
