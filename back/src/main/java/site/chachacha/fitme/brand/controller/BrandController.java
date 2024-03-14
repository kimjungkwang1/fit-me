package site.chachacha.fitme.brand.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.brand.dto.BrandResponse;
import site.chachacha.fitme.brand.service.BrandService;

@RequestMapping("/api/brands")
@RequiredArgsConstructor
@RestController
public class BrandController {

    private final BrandService brandService;

    // 브랜드 목록 조회
    @GetMapping
    public ResponseEntity<List<BrandResponse>> getBrands() {
        List<BrandResponse> responses = brandService.getBrands();
        return ResponseEntity.ok(responses);
    }
}
