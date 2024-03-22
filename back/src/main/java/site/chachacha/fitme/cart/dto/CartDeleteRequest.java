package site.chachacha.fitme.cart.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDeleteRequest {

    private List<Long> cartIds;
}
