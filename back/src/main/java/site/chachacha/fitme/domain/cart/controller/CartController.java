package site.chachacha.fitme.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.common.annotation.MemberId;
import site.chachacha.fitme.domain.cart.dto.CartCreateRequest;
import site.chachacha.fitme.domain.cart.dto.CartDeleteRequest;
import site.chachacha.fitme.domain.cart.dto.CartListResponse;
import site.chachacha.fitme.domain.cart.service.CartService;

@RequestMapping("/api/cart/products")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    // 장바구니 상품 추가
    @PostMapping("/{productId}")
    public ResponseEntity<Void> createCartProduct(@RequestBody @Validated CartCreateRequest request, @PathVariable(name = "productId") Long productId,
        @MemberId Long memberId) {
        cartService.createCartProduct(request, productId, memberId);
        return ResponseEntity.noContent().build();
    }

    // 장바구니 상품 목록 조회
    @GetMapping
    public ResponseEntity<CartListResponse> getCartProducts(@MemberId Long memberId) {
        CartListResponse response = cartService.getCartProducts(memberId);
        return ResponseEntity.ok(response);
    }

    // 장바구니 상품 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeCartProducts(@RequestBody @Validated CartDeleteRequest request, @MemberId Long memberId) {
        cartService.removeCartProducts(request, memberId);
        return ResponseEntity.noContent().build();
    }
}

