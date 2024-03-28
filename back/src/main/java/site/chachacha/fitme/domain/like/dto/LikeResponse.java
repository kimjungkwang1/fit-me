package site.chachacha.fitme.domain.like.dto;

import lombok.Getter;

@Getter
public class LikeResponse {

    private int likeCount;

    public LikeResponse(int likeCount) {
        this.likeCount = likeCount;
    }
}
