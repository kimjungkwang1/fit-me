package site.chachacha.fitme.cart.dto;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class CartRequest {

    @Valid
    private List<CartOptionRequest> options;
}
