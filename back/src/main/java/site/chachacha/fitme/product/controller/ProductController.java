package site.chachacha.fitme.product.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.product.dto.ProductDetailResponse;
import site.chachacha.fitme.product.dto.ProductResponse;
import site.chachacha.fitme.product.dto.ProductSearchRequest;
import site.chachacha.fitme.product.service.ProductService;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    //TODO: 커스텀 어노테이션 구현 후 변경 예정
    private Long meberId = 1L;

    private final ProductService productService;

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@ModelAttribute @Validated ProductSearchRequest request) {
        List<ProductResponse> responses = productService.getProducts(request);
        return ResponseEntity.ok(responses);
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable Long productId) {
        ProductDetailResponse response = productService.getProduct(productId, meberId);
        return ResponseEntity.ok(response);
    }
}
