package site.chachacha.fitme.domain.product.dto;

import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import site.chachacha.fitme.domain.brand.dto.BrandResponse;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.tag.dto.TagResponse;

@Getter
@SuperBuilder
public class ProductDetailResponse extends ProductResponse {

    private List<DetailImageResponse> detailImages;
    private boolean liked;
    private List<TagResponse> tags;

    public static ProductDetailResponse of(Product product, Boolean liked, Double reviewRating, Integer reviewCount) {

        List<TagResponse> tags = product.getProductTags()
            .stream()
            .map(productTag -> TagResponse.from(productTag.getTag()))
            .toList();

        return ProductDetailResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .mainImages(product.getMainImage().stream().map(MainImageResponse::from).toList())
            .brand(BrandResponse.from(product.getBrand()))
            .likeCount(product.getLikeCount())
            .reviewRating(reviewRating)
            .reviewCount(reviewCount)
            .detailImages(product.getDetailImage().stream().map(DetailImageResponse::from).toList())
            .liked(liked)
            .tags(tags)
            .build();
    }
}
