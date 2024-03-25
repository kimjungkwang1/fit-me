package site.chachacha.fitme.domain.cart.dto;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class CartCreateRequest {

    @Valid
    private List<CartOptionRequest> options;
}
