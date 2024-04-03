package site.chachacha.fitme.domain.dressroom.repository;

import java.util.List;
import java.util.Optional;
import site.chachacha.fitme.domain.dressroom.entity.DressRoom;

public interface DressRoomQueryDslRepository {

    List<DressRoom> findNoOffsetByMemberId(Long memberId, Long dressRoomId);

    Optional<DressRoom> findByIdAndMemberId(Long memberId, Long dressRoomId);

    List<DressRoom> findByModelAndProductTopAndProductBottom(Long modelId,
        Long productTopId,
        Long productBottomId);

    List<DressRoom> findByModelAndProductTopAndProductBottomIsNull(Long modelId,
        Long productTopId);

    List<DressRoom> findByModelAndProductBottomAndProductTopIsNull(Long modelId,
        Long productBottomId);

    Boolean findByProductTopAndNull(Long productTopId);

    Boolean findByProductBottomAndNull(Long productBottomId);
}
