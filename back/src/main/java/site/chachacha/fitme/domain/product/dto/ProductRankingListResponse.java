package site.chachacha.fitme.product.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductRankingListResponse {

    private List<ProductRankingResponse> rankings;
    private String updatedTime;

    public ProductRankingListResponse(List<ProductRankingResponse> rankings, String updatedTime) {
        this.rankings = rankings;
        this.updatedTime = updatedTime;
    }
}
