package site.chachacha.fitme.cart.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.cart.dto.CartListResponse;
import site.chachacha.fitme.cart.dto.CartOptionRequest;
import site.chachacha.fitme.cart.dto.CartRequest;
import site.chachacha.fitme.cart.dto.CartResponse;
import site.chachacha.fitme.cart.entity.Cart;
import site.chachacha.fitme.cart.exception.DuplicateCartException;
import site.chachacha.fitme.cart.exception.InvalidProductRelationException;
import site.chachacha.fitme.cart.repository.CartRepository;
import site.chachacha.fitme.member.entity.Member;
import site.chachacha.fitme.member.exception.NoSuchMemberException;
import site.chachacha.fitme.member.repository.MemberRepository;
import site.chachacha.fitme.product.entity.Product;
import site.chachacha.fitme.product.entity.ProductOption;
import site.chachacha.fitme.product.entity.ProductSize;
import site.chachacha.fitme.product.exception.ProductNotFoundException;
import site.chachacha.fitme.product.exception.ProductOptionNotFoundException;
import site.chachacha.fitme.product.exception.ProductSizeNotFoundException;
import site.chachacha.fitme.product.repository.ProductOptionRepository;
import site.chachacha.fitme.product.repository.ProductRepository;
import site.chachacha.fitme.product.repository.ProductSizeRepository;

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
    public void createCartProduct(CartRequest request, Long productId, Long memberId) {
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
        int totalCartPrice = cartResponses.stream().mapToInt(CartResponse::getProductTotalPrice).sum();
        return new CartListResponse(cartResponses, totalProductCount, totalCartPrice);
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
}
