package site.chachacha.fitme.domain.dressroom.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.domain.dressroom.dto.DressRoomResponse;
import site.chachacha.fitme.domain.dressroom.entity.DressRoom;
import site.chachacha.fitme.domain.dressroom.entity.Model;
import site.chachacha.fitme.domain.dressroom.repository.DressRoomRepository;
import site.chachacha.fitme.domain.dressroom.repository.ModelRepository;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.member.repository.MemberRepository;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class DressRoomService {

    private final DressRoomRepository dressRoomRepository;
    private final ModelRepository modelRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    // DressRoom 목록 조회
    public List<DressRoomResponse> findNoOffsetByMemberId(Long memberId, Long dressRoomId) {
        return dressRoomRepository.findNoOffsetByMemberId(memberId, dressRoomId).stream()
            .map(DressRoomResponse::of)
            .collect(toList());
    }

    // DressRoom 조회
    public Optional<DressRoomResponse> findByIdAndMemberId(Long memberId, Long dressRoomId) {
        return dressRoomRepository.findByIdAndMemberId(memberId, dressRoomId)
            .map(DressRoomResponse::of);
    }

    // DressRoom 생성
    @Transactional
    public DressRoom createDressRoom(Long memberId, Long modelId, Long productTopId,
        Long productBottomId) {
        Member member = memberRepository.findNotDeletedById(memberId)
            .orElseThrow(() -> new GoneException("존재하지 않는 회원입니다."));

        Model model = modelRepository.findById(modelId)
            .orElseThrow(() -> new GoneException("존재하지 않는 모델입니다."));

        Product top = productRepository.findById(productTopId)
            .orElseThrow(() -> new GoneException("존재하지 않는 상의입니다."));

        Product bottom = productRepository.findById(productBottomId)
            .orElseThrow(() -> new GoneException("존재하지 않는 하의입니다."));

        DressRoom dressRoom = DressRoom.builder()
            .model(model)
            .productTop(top)
            .productBottom(bottom)
            .member(member)
            .build();

        return dressRoomRepository.save(dressRoom);
    }

    // DressRoom 삭제
    @Transactional
    public void deleteDressRoom(Long memberId, Long dressRoomId) {
        DressRoom dressRoom = dressRoomRepository.findByIdAndMemberId(memberId, dressRoomId)
            .orElseThrow(() -> new GoneException("존재하지 않는 드레스룸입니다."));

        dressRoomRepository.delete(dressRoom);
    }
}
