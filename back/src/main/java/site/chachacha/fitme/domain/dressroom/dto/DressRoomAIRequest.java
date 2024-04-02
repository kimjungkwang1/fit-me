package site.chachacha.fitme.domain.dressroom.dto;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class DressRoomAIRequest {

    private Long modelId;
    private Long productTopId;
    private Boolean topAlready;
    private Long productBottomId;
    private Boolean bottomAlready;

    @Builder
    private DressRoomAIRequest(Long modelId, Long productTopId,
        Boolean topAlready,
        Long productBottomId, Boolean bottomAlready) {
        this.modelId = modelId;
        this.productTopId = productTopId;
        this.topAlready = topAlready;
        this.productBottomId = productBottomId;
        this.bottomAlready = bottomAlready;
    }
}
