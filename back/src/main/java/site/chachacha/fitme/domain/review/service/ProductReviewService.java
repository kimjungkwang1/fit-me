package site.chachacha.fitme.domain.review.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.advice.exception.UnauthorizedException;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.member.repository.MemberRepository;
import site.chachacha.fitme.domain.order.repository.OrderProductRepository;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.repository.ProductRepository;
import site.chachacha.fitme.domain.review.dto.ProductReviewRequest;
import site.chachacha.fitme.domain.review.dto.ProductReviewResponseWithMemberNickname;
import site.chachacha.fitme.domain.review.dto.ProductReviewUpdateRequest;
import site.chachacha.fitme.domain.review.entity.ProductReview;
import site.chachacha.fitme.domain.review.exception.DuplicatedReviewException;
import site.chachacha.fitme.domain.review.exception.ImageUploadException;
import site.chachacha.fitme.domain.review.repository.ProductReviewRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductRepository productRepository;
    private final ProductReviewRepository productReviewRepository;
    private final MemberRepository memberRepository;
    private final OrderProductRepository orderProductRepository;
    private final String imgUrl = "/images/reviews/";

    public List<ProductReviewResponseWithMemberNickname> getReviews(Long productId) {
        return productReviewRepository.findAllByProductId(productId).stream()
            .map(ProductReviewResponseWithMemberNickname::of)
            .toList();
    }

    // 리뷰 조회
    public ProductReviewResponseWithMemberNickname getReview(Long reviewId)
        throws GoneException {
        return productReviewRepository.findByIdWithMember(reviewId)
            .map(ProductReviewResponseWithMemberNickname::of)
            .orElseThrow(() -> new GoneException("존재하지 않는 리뷰입니다."));
    }

    // 리뷰 등록
    @Transactional
    public Long createReview(Long memberId, Long productId, ProductReviewRequest request,
        MultipartFile multipartFile)
        throws GoneException, DuplicatedReviewException, IllegalArgumentException, ImageUploadException, IOException {
        // 회원이 있는지 검증
        Member member = memberRepository.findNotDeletedById(memberId)
            .orElseThrow(() -> new GoneException("존재하지 않는 회원입니다."));

        // 구매 내역이 있는지 검증
        if (!orderProductRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new GoneException("구매 내역이 없습니다.");
        }

        // 이미 리뷰를 작성했는지 검증
        if (productReviewRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new DuplicatedReviewException("이미 리뷰를 작성하셨습니다.");
        }

        // 사진 확장자 검증
        String originalFilename = multipartFile.getOriginalFilename();

        if (originalFilename == null) {
            throw new IllegalArgumentException("파일 이름이 없습니다.");
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
            .toLowerCase();

        if (!List.of("jpg", "jpeg", "png", "gif").contains(extension)) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
        }

        // 사진 크기 검증
        if (multipartFile.getSize() > 20 * 1024 * 1024) {
            throw new IllegalArgumentException("파일 크기는 20MB 이하로 업로드해주세요.");
        }

        // 상품 조회
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new GoneException("존재하지 않는 상품입니다."));

        // 리뷰 등록
        ProductReview productReview = ProductReview.builder()
            .rating(request.getRating())
            .content(request.getContent())
            // ToDo: 이미지 url 수정
            .imageUrl("/products/" + productId + "/reviews/")
            .member(member)
            .product(product)
            .build();

        // 저장
        ProductReview newProductReview = productReviewRepository.save(productReview);

        // 사진 업로드
        // ./images/reviews에서 {member_id} 폴더를 만들고, review_id로 파일명을 변경하여 저장
        // 폴더 만들기
        File file = new File("." + imgUrl + member.getId());
        if (!file.exists()) {
            file.mkdirs();
        }

        // 파일 저장
        multipartFile.transferTo(new File(
            "." + imgUrl + member.getId() + "/" + newProductReview.getId() + "." + extension));

        // 저장
        newProductReview.updateImageUrl(newProductReview.getId());
        productReviewRepository.save(newProductReview);
        productRepository.save(product);
        productReviewRepository.save(productReview);

        return newProductReview.getId();
    }

    // 리뷰 수정
    @Transactional
    public void updateReview(Long memberId, Long reviewId,
        ProductReviewUpdateRequest request) throws GoneException, UnauthorizedException {
        // 해당 리뷰가 있는지 검증
        ProductReview productReviewWithMember = productReviewRepository.findByIdWithMember(reviewId)
            .orElseThrow(() -> new GoneException("존재하지 않는 리뷰입니다."));

        // 해당 리뷰가 회원의 리뷰인지 검증
        if (!productReviewWithMember.getMember().getId().equals(memberId)) {
            throw new UnauthorizedException("해당 리뷰는 회원의 리뷰가 아닙니다.");
        }

        // 회원의 상태가 삭제된 상태인지 검증
        Member member = productReviewWithMember.getMember();
        if (member.isDeleted()) {
            throw new GoneException("삭제된 회원입니다.");
        }

        // 리뷰 수정
        productReviewWithMember.updateReview(request.getContent());

        // 저장
        memberRepository.save(member);
        productReviewRepository.save(productReviewWithMember);
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long memberId, Long reviewId)
        throws GoneException, UnauthorizedException {
        // 해당 리뷰가 있는지 검증
        ProductReview productReviewWithMemberAndProduct = productReviewRepository.findByIdWithMemberAndProduct(
                reviewId)
            .orElseThrow(() -> new GoneException("존재하지 않는 리뷰입니다."));

        // 해당 리뷰가 회원의 리뷰인지 검증
        if (!productReviewWithMemberAndProduct.getMember().getId().equals(memberId)) {
            throw new UnauthorizedException("해당 리뷰는 회원의 리뷰가 아닙니다.");
        }

        // 회원의 상태가 삭제된 상태인지 검증
        if (productReviewWithMemberAndProduct.getMember().isDeleted()) {
            throw new GoneException("삭제된 회원입니다.");
        }

        // count & rating 감소
        Product product = productReviewWithMemberAndProduct.getProduct();
        product.deleteReview(productReviewWithMemberAndProduct.getRating());
        productRepository.save(product);

        // 리뷰 삭제
        productReviewRepository.delete(productReviewWithMemberAndProduct);
    }
}
