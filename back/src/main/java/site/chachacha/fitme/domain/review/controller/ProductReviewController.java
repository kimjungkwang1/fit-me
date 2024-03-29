package site.chachacha.fitme.domain.review.controller;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.advice.exception.UnauthorizedException;
import site.chachacha.fitme.common.annotation.MemberId;
import site.chachacha.fitme.domain.review.dto.ProductReviewRequest;
import site.chachacha.fitme.domain.review.dto.ProductReviewUpdateRequest;
import site.chachacha.fitme.domain.review.exception.DuplicatedReviewException;
import site.chachacha.fitme.domain.review.exception.ImageUploadException;
import site.chachacha.fitme.domain.review.service.ProductReviewService;

@RestController
@RequestMapping(value = "/api/products")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    // 리뷰 조회
    @GetMapping("/{productId}/reviews")
    public ResponseEntity<?> getReviews(@PathVariable(name = "productId") Long productId) {
        return ResponseEntity.ok(productReviewService.getReviews(productId));
    }

    // 리뷰 등록
    @PostMapping(value = "/{productId}/reviews", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReview(@MemberId Long memberId,
        @PathVariable(name = "productId") Long productId,
        @Validated @RequestPart(name = "productReviewRequest") ProductReviewRequest productReviewRequest,
        @RequestPart(value = "image") MultipartFile multipartFile)
        throws GoneException, DuplicatedReviewException, IllegalArgumentException, ImageUploadException {
        // 리뷰 등록
        productReviewService.createReview(memberId, productId, productReviewRequest, multipartFile);

        return ResponseEntity.ok().build();
    }

    // 리뷰 수정
    @PatchMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<?> updateReview(@MemberId Long memberId,
        @PathVariable(name = "productId") Long productId,
        @PathVariable(name = "reviewId") Long reviewId,
        @Validated @RequestBody ProductReviewUpdateRequest request)
        throws GoneException, UnauthorizedException {
        // 리뷰 수정
        productReviewService.updateReview(memberId, reviewId, request);

        return ResponseEntity.ok().build();
    }

    // 리뷰 삭제
    @DeleteMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@MemberId Long memberId,
        @PathVariable(name = "productId") Long productId,
        @PathVariable(name = "reviewId") Long reviewId)
        throws GoneException, UnauthorizedException {
        // 리뷰 삭제
        productReviewService.deleteReview(memberId, reviewId);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<?> handleImageUploadException(ImageUploadException e) {
        return ResponseEntity.status(e.status()).body(e.getMessage());
    }
}
