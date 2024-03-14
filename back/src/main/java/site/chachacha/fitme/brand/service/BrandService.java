package site.chachacha.fitme.brand.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.brand.dto.BrandResponse;
import site.chachacha.fitme.brand.entity.Brand;
import site.chachacha.fitme.brand.repository.BrandRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;

    // 브랜드 목록 조회
    public List<BrandResponse> getBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
            .map(BrandResponse::from)
            .collect(Collectors.toList());
    }

}
