package site.chachacha.fitme.domain.dressroom.repository;

import java.util.List;
import java.util.Optional;
import site.chachacha.fitme.domain.dressroom.entity.DressRoom;

public interface DressRoomQueryDslRepository {

    List<DressRoom> findNoOffsetByMemberId(Long memberId, Long dressRoomId);

    Optional<DressRoom> findByIdAndMemberId(Long memberId, Long dressRoomId);

    Optional<DressRoom> findByModelAndProductTopAndProductBottom(Long modelId, Long productTopId,
        Long productBottomId);
}
