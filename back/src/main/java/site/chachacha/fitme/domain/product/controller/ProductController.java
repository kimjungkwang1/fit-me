package site.chachacha.fitme.domain.product.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.common.annotation.MemberId;
import site.chachacha.fitme.domain.product.dto.ProductDetailResponse;
import site.chachacha.fitme.domain.product.dto.ProductOptionResponse;
import site.chachacha.fitme.domain.product.dto.ProductRankingListResponse;
import site.chachacha.fitme.domain.product.dto.ProductResponse;
import site.chachacha.fitme.domain.product.dto.ProductSearchRequest;
import site.chachacha.fitme.domain.product.service.ProductService;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    // 실시간 상품 랭킹 조회
    @GetMapping("/rankings")
    public ResponseEntity<ProductRankingListResponse> getProductRankings(
        @RequestParam(defaultValue = "0", name = "lastRank") Integer lastRank,
        @RequestParam(defaultValue = "30", name = "size") Integer size) {
        ProductRankingListResponse responses = productService.getProductRankings(lastRank, size);
        return ResponseEntity.ok(responses);
    }

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@ModelAttribute @Validated ProductSearchRequest request) {
        List<ProductResponse> responses = productService.getProducts(request);
        return ResponseEntity.ok(responses);
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable(name = "productId") Long productId, @MemberId Long memberId) {
        ProductDetailResponse response = productService.getProduct(productId, memberId);
        return ResponseEntity.ok(response);
    }

    // 추천 상품 조회
    @GetMapping("/{productId}/recommendations")
    public ResponseEntity<List<ProductResponse>> getRecommendationProducts(@PathVariable(name = "productId") Long productId) {
        List<ProductResponse> responses = productService.getRecommendationProducts(productId);
        return ResponseEntity.ok(responses);
    }

    // 상품 옵션 목록 조회
    @GetMapping("/{productId}/options")
    public ResponseEntity<List<ProductOptionResponse>> getProductOptions(@PathVariable(name = "productId") Long productId) {
        List<ProductOptionResponse> responses = productService.getProductOptions(productId);
        return ResponseEntity.ok(responses);
    }

    // 좋아요한 상품 목록 조회
    @GetMapping("/favorites")
    public ResponseEntity<List<ProductResponse>> getFavoriteProducts(@MemberId Long memberId) {
        List<ProductResponse> responses = productService.getFavoriteProducts(memberId);
        return ResponseEntity.ok(responses);
    }
}
