package site.chachacha.fitme.domain.dressroom.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.chachacha.fitme.domain.dressroom.entity.DressRoom;
import site.chachacha.fitme.domain.product.entity.Product;

public interface DressRoomRepository extends JpaRepository<DressRoom, Long>,
    DressRoomQueryDslRepository {

    List<DressRoom> findNoOffsetByMemberId(Long memberId, Long dressRoomId);

    Optional<DressRoom> findByIdAndMemberId(Long memberId, Long dressRoomId);

    List<DressRoom> findByModelAndProductTopAndProductBottom(Long modelId, Long productTopId,
        Long productBottomId);

    List<DressRoom> findByModelAndProductTopAndProductBottomIsNull(Long modelId,
        Long productTopId);

    List<DressRoom> findByModelAndProductBottomAndProductTopIsNull(Long modelId,
        Long productBottomId);

    boolean existsByProductTopAndProductBottom(Product productTop, Product productBottom);

    boolean existsByProductTopAndProductBottomIsNull(Product productTop);

    boolean existsByProductBottomAndProductTopIsNull(Product productBottom);

    Boolean findByProductTopAndNull(Long productTopId);

    Boolean findByProductBottomAndNull(Long productBottomId);
}
