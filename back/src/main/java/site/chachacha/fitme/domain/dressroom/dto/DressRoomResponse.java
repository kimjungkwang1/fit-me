package site.chachacha.fitme.domain.dressroom.dto;

import static lombok.AccessLevel.PROTECTED;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.domain.dressroom.entity.DressRoom;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class DressRoomResponse {
    @NotNull
    private Long id;

    private String imageUrl;

    private DressRoomResponse(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public static DressRoomResponse of(DressRoom dressRoom) {
        return new DressRoomResponse(dressRoom.getId(), dressRoom.getImageUrl());
    }
}
