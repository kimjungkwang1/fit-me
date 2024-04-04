package site.chachacha.fitme.domain.cart.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDeleteRequest {

    private List<Long> cartIds;
}
