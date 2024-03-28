package site.chachacha.fitme.domain.cart.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.domain.cart.dto.CartCreateRequest;
import site.chachacha.fitme.domain.cart.dto.CartDeleteRequest;
import site.chachacha.fitme.domain.cart.dto.CartListResponse;
import site.chachacha.fitme.domain.cart.dto.CartOptionRequest;
import site.chachacha.fitme.domain.cart.dto.CartResponse;
import site.chachacha.fitme.domain.cart.dto.CartUpdateRequest;
import site.chachacha.fitme.domain.cart.entity.Cart;
import site.chachacha.fitme.domain.cart.exception.CartNotFoundException;
import site.chachacha.fitme.domain.cart.exception.DuplicateCartException;
import site.chachacha.fitme.domain.cart.exception.InvalidProductRelationException;
import site.chachacha.fitme.domain.cart.exception.UnauthorizedCartAccessException;
import site.chachacha.fitme.domain.cart.repository.CartRepository;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.member.exception.NoSuchMemberException;
import site.chachacha.fitme.domain.member.repository.MemberRepository;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;
import site.chachacha.fitme.domain.product.entity.ProductSize;
import site.chachacha.fitme.domain.product.exception.ProductNotFoundException;
import site.chachacha.fitme.domain.product.exception.ProductOptionNotFoundException;
import site.chachacha.fitme.domain.product.exception.ProductSizeNotFoundException;
import site.chachacha.fitme.domain.product.repository.ProductOptionRepository;
import site.chachacha.fitme.domain.product.repository.ProductRepository;
import site.chachacha.fitme.domain.product.repository.ProductSizeRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductSizeRepository productSizeRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    // 장바구니 상품 추가
    @Transactional
    public void createCartProduct(CartCreateRequest request, Long productId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchMemberException::new);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        request.getOptions().forEach(option -> createSingleCartProduct(member, product, option));
    }

    // 장바구니 상품 목록 조회
    public CartListResponse getCartProducts(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchMemberException::new);
        List<Cart> carts = cartRepository.findByMember(member);
        List<CartResponse> cartResponses = carts.stream().map(CartResponse::from).toList();
        int totalProductCount = cartResponses.size();
        return new CartListResponse(cartResponses, totalProductCount);
    }

    // 장바구니 상품 수량 업데이트
    @Transactional
    public void modifyCartProductQuantity(Long memberId, CartUpdateRequest request) {
        Member member = memberRepository.findNotDeletedById(memberId).orElseThrow(NoSuchMemberException::new);
        Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new CartNotFoundException(request.getCartId()));
        validateCartOwnership(member, cart);
        cart.updateQuantity(request.getQuantity());
    }

    // 장바구니 상품 삭제
    @Transactional
    public void removeCartProducts(CartDeleteRequest request, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchMemberException::new);
        // 요청된 모든 장바구니 ID가 주어진 회원에 속하는지 한 번에 확인
        List<Cart> carts = cartRepository.findAllByIdAndMemberId(request.getCartIds(), memberId);

        validateCartListAndIdSize(carts.size(), request.getCartIds().size());
        cartRepository.deleteAll(carts);
    }

    private void createSingleCartProduct(Member member, Product product, CartOptionRequest option) {
        ProductOption productOption = productOptionRepository.findById(option.getProductOptionId())
            .orElseThrow(() -> new ProductOptionNotFoundException(option.getProductOptionId()));

        ProductSize productSize = productSizeRepository.findById(option.getProductSizeId())
            .orElseThrow(() -> new ProductSizeNotFoundException(option.getProductSizeId()));

        validateProductOptionAndSize(product, productOption, productSize);
        validateDuplicateCart(member, product, productOption, productSize);

        Cart cart = Cart.builder()
            .product(product)
            .member(member)
            .productOption(productOption)
            .productSize(productSize)
            .quantity(option.getQuantity())
            .build();

        cartRepository.save(cart);
    }

    private void validateDuplicateCart(Member member, Product product, ProductOption productOption, ProductSize productSize) {
        if (cartRepository.existsByMemberAndProductAndProductOptionAndProductSize(member, product, productOption, productSize)) {
            throw new DuplicateCartException(productOption.getId());
        }
    }

    private void validateProductOptionAndSize(Product product, ProductOption productOption, ProductSize productSize) {
        if (!product.equals(productOption.getProduct()) || !productOption.equals(productSize.getProductOption())) {
            throw new InvalidProductRelationException();
        }
    }

    private void validateCartOwnership(Member member, Cart cart) {
        if (!cart.getMember().equals(member)) {
            throw new UnauthorizedCartAccessException();
        }
    }

    private void validateCartListAndIdSize(int cartsSize, int cartIdsSize) {
        if (cartsSize != cartIdsSize) {
            throw new UnauthorizedCartAccessException();
        }
    }
}
