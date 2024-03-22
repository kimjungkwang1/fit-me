package site.chachacha.fitme.cart.controller;

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
import site.chachacha.fitme.annotation.MemberId;
import site.chachacha.fitme.cart.dto.CartCreateRequest;
import site.chachacha.fitme.cart.dto.CartDeleteRequest;
import site.chachacha.fitme.cart.dto.CartListResponse;
import site.chachacha.fitme.cart.service.CartService;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    // 장바구니 상품 추가
    @PostMapping("/products/{productId}/carts")
    public ResponseEntity<Void> createCartProduct(@RequestBody @Validated CartCreateRequest request, @PathVariable(name = "productId") Long productId,
        @MemberId Long memberId) {
        cartService.createCartProduct(request, productId, memberId);
        return ResponseEntity.noContent().build();
    }

    // 장바구니 상품 목록 조회
    @GetMapping("/carts")
    public ResponseEntity<CartListResponse> getCartProducts(@MemberId Long memberId) {
        CartListResponse response = cartService.getCartProducts(memberId);
        return ResponseEntity.ok(response);
    }

    // 장바구니 상품 삭제 구현
    @DeleteMapping("/carts")
    public ResponseEntity<Void> removeCartProducts(@RequestBody @Validated CartDeleteRequest request, @MemberId Long memberId) {
        cartService.removeCartProducts(request, memberId);
        return ResponseEntity.noContent().build();
    }
}

