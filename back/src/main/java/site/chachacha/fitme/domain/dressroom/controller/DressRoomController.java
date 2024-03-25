package site.chachacha.fitme.domain.dressroom.controller;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.common.annotation.MemberId;
import site.chachacha.fitme.domain.dressroom.dto.DressRoomResponse;
import site.chachacha.fitme.domain.dressroom.entity.Model;
import site.chachacha.fitme.domain.dressroom.service.DressRoomService;
import site.chachacha.fitme.domain.dressroom.service.ModelService;

@RestController
@RequestMapping(value = "/api/dressroom", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DressRoomController {

    private final DressRoomService dressRoomService;
    private final ModelService modelService;

    // 모델 조회
    @GetMapping(value = "/models")
    public ResponseEntity<?> getModels() {
        List<Model> allModels = modelService.findAllModels();

        return ResponseEntity.ok(allModels);
    }

    // DressRoom 목록 조회
    @GetMapping(value = "/list")
    public List<DressRoomResponse> getDressRoomList(@MemberId Long memberId,
        @RequestParam(name = "dressRoomId", required = false) Long dressRoomId) {
        // 회원의 id로 DressRoom 목록 조회
        return dressRoomService.findNoOffsetByMemberId(memberId, dressRoomId);
    }

    // DressRoom 조회
    @GetMapping
    public DressRoomResponse getDressRoom(@MemberId Long memberId,
        @NotNull @RequestParam(name = "dressRoomId") Long dressRoomId) throws GoneException {
        // 회원의 id로 DressRoom 조회
        return dressRoomService.findByIdAndMemberId(memberId,
            dressRoomId);
    }

    // DressRoom 생성
    @PostMapping
    public ResponseEntity<?> createDressRoom(@MemberId Long memberId,
        @NotNull @RequestParam(name = "modelId") Long modelId,
        @RequestParam(name = "productTopId", required = false) Long productTopId,
        @RequestParam(name = "productBottomId", required = false) Long productBottomId)
        throws GoneException {
        // DressRoom 생성
        DressRoomResponse dressRoom = dressRoomService.createDressRoom(memberId, modelId,
            productTopId,
            productBottomId);

        // ToDo: AI 서버에 처리 요청

        return ResponseEntity.ok(dressRoom);
    }

    // DressRoom 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteDressRoom(@MemberId Long memberId,
        @NotNull @RequestParam(name = "dressRoomId") Long dressRoomId) throws GoneException {
        // DressRoom 삭제
        dressRoomService.deleteDressRoom(memberId, dressRoomId);

        return ResponseEntity.ok().build();
    }
}
