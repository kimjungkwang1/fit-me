package site.chachacha.fitme.product.dto;

import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import site.chachacha.fitme.brand.dto.BrandResponse;
import site.chachacha.fitme.product.entity.Product;
import site.chachacha.fitme.tag.dto.TagResponse;

@SuperBuilder
@Getter
public class ProductDetailResponse extends ProductResponse {

    private String detailImageUrl;
    private boolean liked;
    private List<TagResponse> tags;

    private ProductDetailResponse(Long id, String name, Integer price, String mainImageUrl, BrandResponse brand, Integer likeCount,
        Double reviewRating, Integer reviewCount, String detailImageUrl, Boolean liked, List<TagResponse> tags) {
        super(id, name, price, mainImageUrl, brand, likeCount, reviewRating, reviewCount);
        this.detailImageUrl = detailImageUrl;
        this.liked = liked;
        this.tags = tags;
    }

    public static ProductDetailResponse of(Product product, Boolean liked, Double reviewRating, Integer reviewCount) {

        List<TagResponse> tags = product.getProductTags()
            .stream()
            .map(productTag -> TagResponse.from(productTag.getTag()))
            .toList();

        return ProductDetailResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .mainImageUrl(product.getMainImageUrl())
            .brand(BrandResponse.from(product.getBrand()))
            .likeCount(product.getLikeCount())
            .reviewRating(reviewRating)
            .reviewCount(reviewCount)
            .detailImageUrl(product.getDetailImageUrl())
            .liked(liked)
            .tags(tags)
            .build();
    }
    
}
