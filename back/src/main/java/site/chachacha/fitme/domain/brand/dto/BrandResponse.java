package site.chachacha.fitme.domain.brand.dto;

import lombok.Getter;
import site.chachacha.fitme.domain.brand.entity.Brand;

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
