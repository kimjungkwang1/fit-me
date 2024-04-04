package site.chachacha.fitme.domain.product.dto;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductSearchRequest {

    private Long lastId;

    private Double lastPopularityScore;

    private Integer lastPrice;

    private Integer size = 30;

    private String keyword;

    private List<String> ageRanges;

    private List<Long> brandIds;

    private List<Long> categoryIds;

    private Integer startPrice;

    private Integer endPrice;

    private String sortBy;
}
