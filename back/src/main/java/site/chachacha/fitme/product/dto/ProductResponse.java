package site.chachacha.fitme.product.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import site.chachacha.fitme.brand.dto.BrandResponse;
import site.chachacha.fitme.product.entity.Product;

@SuperBuilder
@Getter
public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String mainImageUrl;
    private BrandResponse brand;
    private Integer likeCount;
    private Double reviewRating;
    private Integer reviewCount;

    public ProductResponse(Long id, String name, Integer price, String mainImageUrl, BrandResponse brand, Integer likeCount, Double reviewRating,
        Integer reviewCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.mainImageUrl = mainImageUrl;
        this.brand = brand;
        this.likeCount = likeCount;
        this.reviewRating = reviewRating;
        this.reviewCount = reviewCount;
    }

    public static ProductResponse of(Product product, Double reviewRating, Integer reviewCount) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .mainImageUrl(product.getMainImageUrl())
            .brand(BrandResponse.from(product.getBrand()))
            .likeCount(product.getLikeCount())
            .reviewRating(reviewRating)
            .reviewCount(reviewCount)
            .build();
    }
}
