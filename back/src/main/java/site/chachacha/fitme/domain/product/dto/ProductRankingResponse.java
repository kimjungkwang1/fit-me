package site.chachacha.fitme.domain.product.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.chachacha.fitme.domain.brand.dto.BrandResponse;
import site.chachacha.fitme.domain.product.entity.Product;

@Getter
@Setter
@NoArgsConstructor
public class ProductRankingResponse {

    private int rank;
    private Long id;
    private String name;
    private Integer price;
    private List<MainImageResponse> mainImages;
    private BrandResponse brand;
    private int likeCount;
    private double reviewRating;
    private int reviewCount;

    @Builder
    public ProductRankingResponse(int rank, Long id, String name, Integer price, List<MainImageResponse> mainImages, BrandResponse brand,
        int likeCount,
        double reviewRating, int reviewCount) {
        this.rank = rank;
        this.id = id;
        this.name = name;
        this.price = price;
        this.mainImages = mainImages;
        this.brand = brand;
        this.likeCount = likeCount;
        this.reviewRating = reviewRating;
        this.reviewCount = reviewCount;
    }

    public static ProductRankingResponse of(int rank, Product product) {
        return ProductRankingResponse.builder()
            .rank(rank)
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .mainImages(product.getMainImage().stream().map(MainImageResponse::from).toList())
            .brand(BrandResponse.from(product.getBrand()))
            .likeCount(product.getLikeCount())
            .reviewRating(product.getReviewRating())
            .reviewCount(product.getReviewCount())
            .build();
    }

}
