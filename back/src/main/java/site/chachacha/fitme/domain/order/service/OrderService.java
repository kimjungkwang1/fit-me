package site.chachacha.fitme.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.member.repository.MemberRepository;
import site.chachacha.fitme.domain.order.Order;
import site.chachacha.fitme.domain.order.OrderProduct;
import site.chachacha.fitme.domain.order.dto.OrderRequest;
import site.chachacha.fitme.domain.order.repository.OrderProductRepository;
import site.chachacha.fitme.domain.order.repository.OrderRepository;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;
import site.chachacha.fitme.domain.product.entity.ProductSize;
import site.chachacha.fitme.domain.product.repository.ProductOptionRepository;
import site.chachacha.fitme.domain.product.repository.ProductRepository;
import site.chachacha.fitme.domain.product.repository.ProductSizeRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductSizeRepository productSizeRepository;

    // 주문 생성
    @Transactional
    public void createOrder(Long memberId, List<OrderRequest> orderRequests) throws GoneException {
        Member member = memberRepository.findNotDeletedById(memberId)
            .orElseThrow(() -> new GoneException("존재하지 않는 회원입니다."));

        List<OrderProduct> newOrderProducts = new ArrayList<>();

        // orderRequests를 순회하면서
        for (OrderRequest orderRequest : orderRequests) {
            // ProductOption을 찾는다.
            ProductOption productOption = productOptionRepository.findByIdWithProduct(
                    orderRequest.getProductOptionId())
                .orElseThrow(() -> new GoneException("존재하지 않는 ProductOption입니다."));

            // ProductSize를 찾는다.
            ProductSize productSize = productSizeRepository.findByIdAndProductOptionId(
                    orderRequest.getProductSizeId(), orderRequest.getProductOptionId())
                .orElseThrow(() -> new GoneException("존재하지 않는 ProductSize입니다."));

            Product product = productOption.getProduct();

            // OrderProduct를 생성한다.
            OrderProduct orderProduct = OrderProduct.builder()
                .member(member)
                .product(product)
                .productOption(productOption)
                .productSize(productSize)
                .count(orderRequest.getCount())
                .build();

            // 저장
//            productRepository.save(product);
//            productOptionRepository.save(productOption);
//            productSizeRepository.save(productSize);
            orderProductRepository.save(orderProduct);

            // OrderProduct를 리스트에 추가
            newOrderProducts.add(orderProduct);
        }

        // Order 생성
        Order order = Order.builder()
            .member(member)
            .orderProducts(newOrderProducts)
            .build();

        orderRepository.save(order);
    }
}
