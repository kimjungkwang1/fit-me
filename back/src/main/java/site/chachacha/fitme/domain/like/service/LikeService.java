package site.chachacha.fitme.domain.like.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.domain.like.dto.LikeResponse;
import site.chachacha.fitme.domain.like.entity.ProductLike;
import site.chachacha.fitme.domain.like.exception.DuplicateProductLikeException;
import site.chachacha.fitme.domain.like.exception.ProductLikeNotFoundException;
import site.chachacha.fitme.domain.like.repository.ProductLikeRepository;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.member.exception.NoSuchMemberException;
import site.chachacha.fitme.domain.member.repository.MemberRepository;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.exception.ProductNotFoundException;
import site.chachacha.fitme.domain.product.repository.ProductRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public LikeResponse createProductLike(Long productId, Long memberId) {
        Member member = memberRepository.findNotDeletedById(memberId).orElseThrow(NoSuchMemberException::new);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        validateNoDuplicateProductLike(member, product);

        ProductLike productLike = ProductLike.builder()
            .product(product)
            .member(member)
            .build();
        product.addLike(productLike);
        productLikeRepository.save(productLike);
        return new LikeResponse(product.getLikeCount());
    }

    @Transactional
    public LikeResponse removeProductLike(Long productId, Long memberId) {
        Member member = memberRepository.findNotDeletedById(memberId).orElseThrow(NoSuchMemberException::new);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        ProductLike productLike = productLikeRepository.findByProductAndMember(product, member).orElseThrow(ProductLikeNotFoundException::new);

        productLikeRepository.delete(productLike);
        product.deleteLike();
        return new LikeResponse(product.getLikeCount());
    }

    private void validateNoDuplicateProductLike(Member member, Product product) {
        if (productLikeRepository.existsByProductAndMember(product, member)) {
            throw new DuplicateProductLikeException();
        }
    }

}
