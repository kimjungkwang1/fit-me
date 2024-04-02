package site.chachacha.fitme.domain.order.dto;

import static lombok.AccessLevel.PROTECTED;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Validated
public class OrderListRequest {

    @Valid
    private List<OrderRequest> orderRequests;
}
