package site.chachacha.fitme.product.dto;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductSearchRequest {

    private Long lastId;

    private Integer size = 30;

    private Integer gender;

    private String ageRange;

    private List<Long> brandIds;

    private List<Long> categoryIds;

    private Integer startPrice;

    private Integer endPrice;

    private String sortBy;
}
