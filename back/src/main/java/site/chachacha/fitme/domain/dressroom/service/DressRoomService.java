package site.chachacha.fitme.domain.dressroom.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.domain.dressroom.dto.DressRoomResponse;
import site.chachacha.fitme.domain.dressroom.repository.DressRoomRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DressRoomService {
    private final DressRoomRepository dressRoomRepository;

    // DressRoom 목록 조회
    public List<DressRoomResponse> findNoOffsetByMemberId(Long memberId, Long dressRoomId) {
        return dressRoomRepository.findNoOffsetByMemberId(memberId, dressRoomId).stream()
            .map(DressRoomResponse::of)
            .collect(toList());
    }

    // DressRoom 조회
    public Optional<DressRoomResponse> findByIdAndMemberId(Long memberId, Long dressRoomId) {
        return dressRoomRepository.findByIdAndMemberId(memberId, dressRoomId).map(DressRoomResponse::of);
    }
}
