package site.chachacha.fitme.domain.order.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.common.annotation.MemberId;
import site.chachacha.fitme.domain.order.dto.OrderListRequest;
import site.chachacha.fitme.domain.order.exception.NotEnoughStockException;
import site.chachacha.fitme.domain.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders")
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
        @Validated @RequestBody OrderListRequest orderListRequest)
        throws GoneException, NotEnoughStockException {
        Long orderId = orderService.createOrder(memberId, orderListRequest.getOrderRequests());

        return ResponseEntity.created(URI.create("/api/order/" + orderId)).build();
    }

    // 주문 취소
    @DeleteMapping
    public ResponseEntity<Void> cancelOrder(@MemberId Long memberId,
        @RequestParam(name = "orderId") Long orderId)
        throws GoneException {
        orderService.cancelOrder(memberId, orderId);

        return ResponseEntity.noContent().build();
    }
}
