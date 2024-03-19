package site.chachacha.fitme.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.annotation.MemberId;
import site.chachacha.fitme.cart.dto.CartRequest;
import site.chachacha.fitme.cart.service.CartService;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping("{productId}/carts")
    public ResponseEntity<Void> createCartProduct(@RequestBody @Validated CartRequest request, @PathVariable(name = "productId") Long productId,
        @MemberId Long memberId) {
        cartService.createCartProduct(request, productId, memberId);
        return ResponseEntity.noContent().build();
    }
}

