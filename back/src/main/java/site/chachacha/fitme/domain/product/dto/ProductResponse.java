package site.chachacha.fitme.domain.product.dto;

import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import site.chachacha.fitme.domain.brand.dto.BrandResponse;
import site.chachacha.fitme.domain.product.entity.Product;

@Getter
@SuperBuilder
public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private List<MainImageResponse> mainImages;
    private BrandResponse brand;
    private double popularityScore;
    private int likeCount;
    private double reviewRating;
    private int reviewCount;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .mainImages(product.getMainImage().stream().map(MainImageResponse::from).toList())
            .brand(BrandResponse.from(product.getBrand()))
            .popularityScore(product.getMonthlyPopularityScore())
            .likeCount(product.getLikeCount())
            .reviewRating(product.getReviewRating())
            .reviewCount(product.getReviewCount())
            .build();
    }
}
