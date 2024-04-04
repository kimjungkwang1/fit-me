package site.chachacha.fitme.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.member.repository.MemberRepository;
import site.chachacha.fitme.domain.order.dto.OrderRequest;
import site.chachacha.fitme.domain.order.dto.OrderWithOrderProductResponse;
import site.chachacha.fitme.domain.order.entity.Order;
import site.chachacha.fitme.domain.order.entity.OrderProduct;
import site.chachacha.fitme.domain.order.exception.NotEnoughStockException;
import site.chachacha.fitme.domain.order.repository.OrderProductRepository;
import site.chachacha.fitme.domain.order.repository.OrderRepository;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;
import site.chachacha.fitme.domain.product.entity.ProductSize;
import site.chachacha.fitme.domain.product.repository.ProductOptionRepository;
import site.chachacha.fitme.domain.product.repository.ProductRepository;
import site.chachacha.fitme.domain.product.repository.ProductSizeRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductSizeRepository productSizeRepository;

    // 주문 목록 조회
    public List<OrderWithOrderProductResponse> getOrderList(Long memberId, Pageable pageable) {
        List<Order> orders = orderRepository.findAllByMemberId(memberId, pageable);

        return OrderWithOrderProductResponse.of(orders);
    }

    // 주문 생성
    @Transactional
    public Long createOrder(Long memberId, List<OrderRequest> orderRequests)
        throws GoneException, NotEnoughStockException {
        Member member = memberRepository.findNotDeletedByIdWithCart(memberId)
            .orElseThrow(() -> new GoneException("존재하지 않는 회원입니다."));

        List<OrderProduct> newOrderProducts = new ArrayList<>();

        // orderRequests를 순회하면서
        for (OrderRequest orderRequest : orderRequests) {
            // 해당 ProductOption이 존재하는지 확인
            ProductOption productOption = productOptionRepository.findById(
                    orderRequest.getProductOptionId())
                .orElseThrow(() -> new GoneException("존재하지 않는 ProductOption입니다."));

            // 해당 ProductSize가 존재하는지 확인
            ProductSize productSize = productSizeRepository.findByIdAndProductOptionId(
                    orderRequest.getProductSizeId(), orderRequest.getProductOptionId())
                .orElseThrow(() -> new GoneException("존재하지 않는 ProductSize입니다."));

            Product product = productRepository.findByIdWithBrand(orderRequest.getProductId())
                .orElseThrow(() -> new GoneException("존재하지 않는 Product입니다."));

            member.getCarts().stream()
                .filter(cart -> cart.getProduct().getId().equals(orderRequest.getProductId()))
                // Cart에서 해당 상품을 찾는다.
                .findFirst()
                // Cart에서 해당 상품을 삭제한다.
                .ifPresent(member::removeProductFromCart);

            // OrderProduct를 생성한다.
            OrderProduct orderProduct = OrderProduct.builder()
                .member(member)
                .brand(product.getBrand())
                .product(product)
                .productOption(productOption)
                .productSize(productSize)
                .count(orderRequest.getCount())
                .build();

            // 저장
            orderProductRepository.save(orderProduct);

            // OrderProduct를 리스트에 추가
            newOrderProducts.add(orderProduct);
        }

        // Order 생성
        Order order = Order.builder()
            .member(member)
            .orderProducts(newOrderProducts)
            .build();

        // 저장
        return orderRepository.save(order).getId();
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long memberId, Long orderId) throws GoneException {
        Member member = memberRepository.findNotDeletedById(memberId)
            .orElseThrow(() -> new GoneException("존재하지 않는 회원입니다."));

        Order order = orderRepository.findByIdAndMemberId(orderId, memberId)
            .orElseThrow(() -> new GoneException("존재하지 않는 주문입니다."));

        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderIdWithProductSize(
            orderId);

        // 주문 취소 시, 재고 수량을 복구한다.
        // OrderProduct를 순회하면서
        for (OrderProduct orderProduct : orderProducts) {
            // ProductSize를 조회한다.
            ProductSize productSize = orderProduct.getProductSize();

            // ProductSize의 재고 수량을 복구한다.
            productSize.cancelOrderProduct(orderProduct.getCount());
        }

        order.cancel();

        // 저장
        memberRepository.save(member);
    }
}
