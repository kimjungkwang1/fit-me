package site.chachacha.fitme.domain.order.controller;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.domain.order.dto.OrderRequest;
import site.chachacha.fitme.domain.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/order", consumes = APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Validated List<OrderRequest> orderRequests,
        HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        orderService.createOrder(memberId, orderRequests);

        return ResponseEntity.noContent().build();
    }

}
