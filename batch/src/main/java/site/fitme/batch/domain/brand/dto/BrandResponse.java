package site.fitme.batch.domain.brand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.fitme.batch.domain.brand.entity.Brand;

@Setter
@NoArgsConstructor
@Getter
public class BrandResponse {

    private Long id;
    private String name;

    private BrandResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BrandResponse from(Brand brand) {
        return new BrandResponse(brand.getId(), brand.getName());
    }
}
