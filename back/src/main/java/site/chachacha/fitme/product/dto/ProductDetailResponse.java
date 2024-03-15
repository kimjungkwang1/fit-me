package site.chachacha.fitme.product.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import site.chachacha.fitme.brand.dto.BrandResponse;
import site.chachacha.fitme.product.entity.DetailImage;
import site.chachacha.fitme.product.entity.MainImage;
import site.chachacha.fitme.product.entity.Product;
import site.chachacha.fitme.tag.dto.TagResponse;

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
