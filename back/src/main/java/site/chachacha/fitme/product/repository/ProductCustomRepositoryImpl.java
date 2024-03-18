package site.chachacha.fitme.product.repository;

import static site.chachacha.fitme.brand.entity.QBrand.brand;
import static site.chachacha.fitme.product.entity.QProduct.product;
import static site.chachacha.fitme.review.entity.QProductReview.productReview;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.product.entity.Gender;
import site.chachacha.fitme.product.entity.Product;

@RequiredArgsConstructor
@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    //Querydsl을 사용하여 JPA 쿼리를 생성하고 실행하기 위한 클래스
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findAllByProductConditions(Long lastId, Integer size, Integer gender, String ageRange, List<Long> brandIds,
        List<Long> categoryIds, Integer startPrice, Integer endPrice, String sortBy) {

        return queryFactory
            .selectFrom(product)
            .leftJoin(product.brand, brand).fetchJoin()
            .leftJoin(product.productReviews, productReview).fetchJoin()
            .where(
                gtLastId(lastId),
                eqGender(gender),
                eqAgeRange(ageRange),
                inBrandIds(brandIds),
                inCategoryIds(categoryIds),
                goeStartPrice(startPrice),
                loeEndPrice(endPrice)
            )
            .limit(size)
            .orderBy(getOrderSpecifier(sortBy))
            .fetch();
    }

    // 정렬 조건 반환
    private OrderSpecifier<?>[] getOrderSpecifier(String sortBy) {

        // 최신순 정렬
        if ("latest".equals(sortBy)) {
            return new OrderSpecifier<?>[]{product.id.desc()};
        }

        // 인기순 정렬
        // 구매(7), 좋아요(2), 조회(1)
        return new OrderSpecifier<?>[]{product.monthlyPopularityScore.desc()};
    }

    private BooleanExpression gtLastId(Long lastId) {
        if (lastId == null) {
            return null;
        }
        return product.id.gt(lastId);
    }

    private BooleanExpression eqGender(Integer gender) {
        if (gender == null) {
            return null;
        }
        return product.gender.eq(Gender.fromValue(gender));
    }

    private BooleanExpression eqAgeRange(String ageRange) {
        if (StringUtils.isBlank(ageRange)) {
            return null;
        }
        return product.ageRange.eq(ageRange);
    }

    private BooleanExpression inBrandIds(List<Long> brandIds) {
        if (brandIds == null) {
            return null;
        }
        return product.brand.id.in(brandIds);
    }

    private BooleanExpression inCategoryIds(List<Long> categoryIds) {
        if (categoryIds == null) {
            return null;
        }
        return product.category.id.in(categoryIds);
    }

    private BooleanExpression goeStartPrice(Integer startPrice) {
        if (startPrice == null) {
            return null;
        }
        return product.price.goe(startPrice);
    }

    private BooleanExpression loeEndPrice(Integer endPrice) {
        if (endPrice == null) {
            return null;
        }
        return product.price.loe(endPrice);
    }


}