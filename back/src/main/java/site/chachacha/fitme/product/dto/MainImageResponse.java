package site.chachacha.fitme.product.dto;

import lombok.Builder;
import lombok.Getter;
import site.chachacha.fitme.product.entity.MainImage;

@Getter
public class MainImageResponse {
    private Long id;
    private String url;

    @Builder
    private MainImageResponse(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public static MainImageResponse from(MainImage mainImage) {
        return MainImageResponse.builder()
            .id(mainImage.getId())
            .url(mainImage.getUrl())
            .build();
    }
}
