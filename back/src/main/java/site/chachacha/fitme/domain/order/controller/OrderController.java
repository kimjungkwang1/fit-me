package site.chachacha.fitme.domain.order.controller;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.common.annotation.MemberId;
import site.chachacha.fitme.domain.order.dto.OrderRequest;
import site.chachacha.fitme.domain.order.exception.NotEnoughStockException;
import site.chachacha.fitme.domain.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/order")
public class OrderController {

    private final OrderService orderService;

    // 주문 내역 조회
    @GetMapping
    public ResponseEntity<?> getOrderList(@MemberId Long memberId,
        @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrderList(memberId, pageable));
    }

    // 주문 생성
    @PostMapping
    public ResponseEntity<Void> createOrder(@MemberId Long memberId,
        @Validated @RequestBody List<OrderRequest> orderRequests)
        throws GoneException, NotEnoughStockException {
        Long orderId = orderService.createOrder(memberId, orderRequests);

        return ResponseEntity.created(URI.create("/api/order/" + orderId)).build();
    }

    // 주문 취소
    @DeleteMapping
    public ResponseEntity<Void> cancelOrder(@MemberId Long memberId, Long orderId)
        throws GoneException {
        orderService.cancelOrder(memberId, orderId);

        return ResponseEntity.noContent().build();
    }
}
