package site.chachacha.fitme.product.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.product.dto.ProductResponse;
import site.chachacha.fitme.product.dto.ProductSearchRequest;
import site.chachacha.fitme.product.service.ProductService;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@ModelAttribute @Validated ProductSearchRequest request) {
        List<ProductResponse> responses = productService.getProducts(request);
        return ResponseEntity.ok(responses);
    }
}
