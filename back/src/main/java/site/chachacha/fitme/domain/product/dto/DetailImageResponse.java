package site.chachacha.fitme.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import site.chachacha.fitme.domain.product.entity.DetailImage;

@Getter
public class DetailImageResponse {
    private Long id;
    private String url;

    @Builder
    private DetailImageResponse(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public static DetailImageResponse from(DetailImage detailImage) {
        return DetailImageResponse.builder()
            .id(detailImage.getId())
            .url(detailImage.getUrl())
            .build();
    }
}
