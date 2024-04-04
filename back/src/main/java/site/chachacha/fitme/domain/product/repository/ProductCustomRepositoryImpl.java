package site.chachacha.fitme.domain.product.repository;

import static site.chachacha.fitme.domain.brand.entity.QBrand.brand;
import static site.chachacha.fitme.domain.product.entity.QProduct.product;
import static site.chachacha.fitme.domain.tag.entity.QProductTag.productTag;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.domain.product.entity.Product;

@RequiredArgsConstructor
@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    //Querydsl을 사용하여 JPA 쿼리를 생성하고 실행하기 위한 클래스
    private final JPAQueryFactory queryFactory;
    private static final String SORT_BY_LATEST = "latest";
    private static final String SORT_BY_PRICE_ASC = "priceAsc";
    private static final String SORT_BY_PRICE_DESC = "priceDesc";

    @Override
    public List<Product> findAllByProductConditions(Long lastId, Double lastPopularityScore, Integer lastPrice, Integer size, String keyword,
        List<String> ageRanges, List<Long> brandIds, List<Long> categoryIds, Integer startPrice, Integer endPrice, String sortBy) {

        return queryFactory
            .selectFrom(product)
            .distinct()
            .leftJoin(product.brand, brand).fetchJoin()
            .leftJoin(product.productTags, productTag)
            .where(
                generateWhereCondition(sortBy, lastId, lastPopularityScore, lastPrice),
                keywordCondition(keyword),
                inAgeRanges(ageRanges),
                inBrandIds(brandIds),
                inCategoryIds(categoryIds),
                goeStartPrice(startPrice),
                loeEndPrice(endPrice)
            )
            .orderBy(getOrderSpecifier(sortBy))
            .limit(size)
            .fetch();
    }

    private BooleanExpression generateWhereCondition(String sortBy, Long lastId, Double lastPopularityScore, Integer lastPrice) {
        if (SORT_BY_LATEST.equals(sortBy)) {
            return ltLastId(lastId);
        }
        if (SORT_BY_PRICE_ASC.equals(sortBy) || SORT_BY_PRICE_DESC.equals(sortBy)) {
            return priceCursorCondition(lastPrice, lastId, sortBy);
        }
        return popularCursorCondition(lastPopularityScore, lastId);
    }

    // 정렬 조건 반환
    private OrderSpecifier<?>[] getOrderSpecifier(String sortBy) {

        // 최신순 정렬
        if (SORT_BY_LATEST.equals(sortBy)) {
            return new OrderSpecifier<?>[]{product.id.desc()};
        }

        // 가격 낮은 순 정렬
        if (SORT_BY_PRICE_ASC.equals(sortBy)) {
            return new OrderSpecifier<?>[]{product.price.asc()};
        }

        // 가격 높은 순 정렬
        if (SORT_BY_PRICE_DESC.equals(sortBy)) {
            return new OrderSpecifier<?>[]{product.price.desc()};
        }

        // 인기순 정렬
        // 구매(8), 좋아요(2)
        return new OrderSpecifier<?>[]{product.monthlyPopularityScore.desc(), product.id.desc()};
    }

    private BooleanExpression ltLastId(Long lastId) {
        if (lastId == null) {
            return null;
        }
        return product.id.lt(lastId); // id < lastId
    }

    private BooleanExpression popularCursorCondition(Double lastPopularityScore, Long lastId) {
        if (lastPopularityScore == null || lastId == null) {
            return null;
        }

        // monthlyPopularityScore가 이전 마지막보다 작거나 같고, 동일할 경우 id가 더 작은 항목을 검색
        BooleanExpression prevMonthlyPopularityScore = product.monthlyPopularityScore.lt(lastPopularityScore);
        BooleanExpression sameMonthlyPopularityScoreButPrevId = product.monthlyPopularityScore.eq(lastPopularityScore)
            .and(product.id.lt(lastId));
        return prevMonthlyPopularityScore.or(sameMonthlyPopularityScoreButPrevId);
    }

    private BooleanExpression priceCursorCondition(Integer lastPrice, Long lastId, String sortBy) {
        if (lastPrice == null || lastId == null) {
            return null;
        }

        BooleanExpression prevPriceCondition = SORT_BY_PRICE_DESC.equals(sortBy) ? product.price.lt(lastPrice) : product.price.gt(lastPrice);

        BooleanExpression samePriceButPrevIdCondition = product.price.eq(lastPrice)
            .and(SORT_BY_PRICE_DESC.equals(sortBy) ? product.id.lt(lastId) : product.id.gt(lastId));

        return prevPriceCondition.or(samePriceButPrevIdCondition);
    }


    private BooleanExpression keywordCondition(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        return product.name.containsIgnoreCase(keyword)
            .or(productTag.tag.name.containsIgnoreCase(keyword));
    }

    private BooleanExpression inAgeRanges(List<String> ageRanges) {
        if (ageRanges == null) {
            return null;
        }
        return product.ageRange.in(ageRanges);
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