package site.fitme.batch.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.fitme.batch.domain.product.entity.MainImage;

@Getter
@Setter
@NoArgsConstructor
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
