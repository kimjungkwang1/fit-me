package site.chachacha.fitme.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.common.annotation.MemberId;
import site.chachacha.fitme.domain.like.dto.LikeResponse;
import site.chachacha.fitme.domain.like.service.LikeService;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    // 상품 좋아요 등록
    @PostMapping("/products/{productId}/like")
    public ResponseEntity<LikeResponse> createProductLike(@PathVariable(name = "productId") Long productId, @MemberId Long memberId) {
        LikeResponse response = likeService.createProductLike(productId, memberId);
        return ResponseEntity.ok(response);
    }

    // 상품 좋아요 삭제
    @DeleteMapping("/products/{productId}/like")
    public ResponseEntity<LikeResponse> removeProductLike(@PathVariable(name = "productId") Long productId, @MemberId Long memberId) {
        LikeResponse response = likeService.removeProductLike(productId, memberId);
        return ResponseEntity.ok(response);
    }

}
