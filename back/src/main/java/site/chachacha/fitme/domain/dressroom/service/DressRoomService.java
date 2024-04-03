package site.chachacha.fitme.domain.dressroom.service;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import site.chachacha.fitme.advice.exception.BadRequestException;
import site.chachacha.fitme.advice.exception.GoneException;
import site.chachacha.fitme.domain.dressroom.dto.DressRoomAIRequest;
import site.chachacha.fitme.domain.dressroom.dto.DressRoomResponse;
import site.chachacha.fitme.domain.dressroom.entity.DressRoom;
import site.chachacha.fitme.domain.dressroom.entity.Model;
import site.chachacha.fitme.domain.dressroom.exception.FileSaveException;
import site.chachacha.fitme.domain.dressroom.exception.InferenceFailureException;
import site.chachacha.fitme.domain.dressroom.repository.DressRoomRepository;
import site.chachacha.fitme.domain.dressroom.repository.ModelRepository;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.member.repository.MemberRepository;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.repository.ProductRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DressRoomService {

    private final DressRoomRepository dressRoomRepository;
    private final ModelRepository modelRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private final String imgUrl = ".images/dressroom/men/";

    // DressRoom 목록 조회
    public List<DressRoomResponse> findNoOffsetByMemberId(Long memberId, Long dressRoomId) {
        return dressRoomRepository.findNoOffsetByMemberId(memberId, dressRoomId).stream()
            .map(DressRoomResponse::of)
            .collect(toList());
    }

    // DressRoom 조회
    public DressRoomResponse findByIdAndMemberId(Long memberId, Long dressRoomId) {
        return DressRoomResponse.of(dressRoomRepository.findByIdAndMemberId(memberId, dressRoomId)
            .orElseThrow(() -> new GoneException("해당 드레스룸이 존재하지 않습니다.")));
    }

    // DressRoom 생성
    @Transactional
    public DressRoomResponse createDressRoom(Long memberId, Long modelId, Long productTopId,
        Long productBottomId) throws BadRequestException, GoneException, InferenceFailureException {
        // productTopId, productBottomId 둘 다 null이면 BadRequestException 발생
        if (productTopId == null && productBottomId == null) {
            throw new BadRequestException("상의나 하의 중 하나는 선택해야 합니다.");
        }

        // member가 존재하지 않으면 GoneException 발생
        Member member = memberRepository.findNotDeletedById(memberId)
            .orElseThrow(() -> new GoneException("존재하지 않는 회원입니다."));

        // model이 존재하지 않으면 GoneException 발생
        Model model = modelRepository.findById(modelId)
            .orElseThrow(() -> new GoneException("존재하지 않는 모델입니다."));

        // DressRoom이 이미 존재하는지 조회
        Optional<DressRoom> existDressRoom = dressRoomRepository.findByMemberIdAndModelIdAndProductTopIdAndProductBottomId(
            memberId, modelId, productTopId, productBottomId);

        // DressRoom이 이미 존재하는 경우
        if (existDressRoom.isPresent()) {
            // 그대로 반환
            return DressRoomResponse.of(existDressRoom.get());
        }

        // 상, 하의 둘 다 요청이 들어온 경우
        if (productTopId != null && productBottomId != null) {
            // 상, 하의 이미 둘 다 드레스룸에 있는지 조회
            List<DressRoom> dressRooms = dressRoomRepository.findByModelAndProductTopAndProductBottom(
                modelId, productTopId, productBottomId);

            // 이미 존재하는 경우
            if (!dressRooms.isEmpty()) {
                // 그대로 반환하면 돼
                DressRoom dressRoom = DressRoom.builder()
                    .model(model)
                    .productTop(dressRooms.get(0).getProductTop())
                    .productBottom(dressRooms.get(0).getProductBottom())
                    .member(member)
                    .build();

                // 이 저장 로직은 움직이지 마세요.
                DressRoom savedDressRoom = dressRoomRepository.save(dressRoom);

                return DressRoomResponse.of(savedDressRoom);
            }

            // 상의, 하의 동시에 입힌게 드레스룸에 없는 경우

            // 상의 이미 드레스룸에 있는지 확인
            List<DressRoom> dressRoomWithTop = dressRoomRepository.findByModelAndProductTopAndProductBottomIsNull(
                modelId, productTopId);

            // 상의 이미 캐싱되어 있는 경우
            if (!dressRoomWithTop.isEmpty()) {
                // 저장
                DressRoom dressRoom = DressRoom.builder()
                    .model(model)
                    .productTop(dressRoomWithTop.get(0).getProductTop())
                    .productBottom(dressRoomWithTop.get(0).getProductBottom())
                    .member(member)
                    .build();

                DressRoom savedDressRoom = dressRoomRepository.save(dressRoom);

                // 캐시된 상의를 기반으로 하의 추가
                requestAIServer(modelId, productTopId, productBottomId, savedDressRoom, true,
                    false);

                return DressRoomResponse.of(savedDressRoom);
            }
            // 상의 캐싱되어 있지 않은 경우
            else {
                // 하의 이미 드레스룸에 있는지 확인
                List<DressRoom> dressRoomWithBottom = dressRoomRepository.findByModelAndProductBottomAndProductTopIsNull(
                    modelId, productBottomId);

                // 하의 이미 캐싱되어 있는 경우
                if (!dressRoomWithBottom.isEmpty()) {
                    // 저장
                    DressRoom dressRoom = DressRoom.builder()
                        .model(model)
                        .productTop(dressRoomWithBottom.get(0).getProductTop())
                        .productBottom(dressRoomWithBottom.get(0).getProductBottom())
                        .member(member)
                        .build();

                    DressRoom savedDressRoom = dressRoomRepository.save(dressRoom);

                    // 캐시된 하의를 기반으로 상의 추가
                    requestAIServer(modelId, productTopId, productBottomId, savedDressRoom, false,
                        true);

                    return DressRoomResponse.of(savedDressRoom);
                }
                // 하의 캐싱되어 있지 않은 경우
                else {
                    // 저장
                    DressRoom dressRoom = DressRoom.builder()
                        .model(model)
                        .productTop(dressRooms.get(0).getProductTop())
                        .productBottom(dressRooms.get(0).getProductBottom())
                        .member(member)
                        .build();

                    DressRoom savedDressRoom = dressRoomRepository.save(dressRoom);

                    // 캐시된 상의, 하의가 없는 경우
                    requestAIServer(modelId, productTopId, productBottomId, savedDressRoom, false,
                        false);

                    return DressRoomResponse.of(savedDressRoom);
                }
            }
        }
        // 상의만 요청이 들어온 경우
        else if (productTopId != null) {
            // 상의만 드레스룸에 있는지 확인
            List<DressRoom> dressRooms = dressRoomRepository.findByModelAndProductTopAndProductBottomIsNull(
                modelId, productTopId);

            // 상의만 있는 드레스룸이 이미 존재하는 경우
            if (!dressRooms.isEmpty()) {
                // 그대로 반환하면 돼
                DressRoom dressRoom = DressRoom.builder()
                    .model(model)
                    .productTop(dressRooms.get(0).getProductTop())
                    .productBottom(dressRooms.get(0).getProductBottom())
                    .member(member)
                    .build();

                // 이 저장 로직은 움직이지 마세요.
                DressRoom savedDressRoom = dressRoomRepository.save(dressRoom);

                return DressRoomResponse.of(savedDressRoom);
            }
            // 상의만 있는 드레스룸이 존재하지 않는 경우
            else {
                // 상의를 조회
                Product top = productRepository.findById(productTopId)
                    .orElseThrow(() -> new GoneException("존재하지 않는 상의입니다."));

                // 저장
                DressRoom dressRoom = DressRoom.builder()
                    .model(model)
                    .productTop(top)
                    .productBottom(null)
                    .member(member)
                    .build();

                DressRoom savedDressRoom = dressRoomRepository.save(dressRoom);

                // 캐시된 상의가 없는 경우
                requestAIServer(modelId, productTopId, productBottomId, savedDressRoom, false,
                    null);

                return DressRoomResponse.of(savedDressRoom);
            }
        }
        // 하의만 요청이 들어온 경우
        else if (productBottomId != null) {
            // 하의만 드레스룸에 있는지 확인
            List<DressRoom> dressRooms = dressRoomRepository.findByModelAndProductBottomAndProductTopIsNull(
                modelId, productBottomId);

            // 하의만 있는 드레스룸이 이미 존재하는 경우
            if (!dressRooms.isEmpty()) {
                // 그대로 반환하면 돼
                DressRoom dressRoom = DressRoom.builder()
                    .model(model)
                    .productTop(dressRooms.get(0).getProductTop())
                    .productBottom(dressRooms.get(0).getProductBottom())
                    .member(member)
                    .build();

                // 이 저장 로직은 움직이지 마세요.
                DressRoom savedDressRoom = dressRoomRepository.save(dressRoom);

                return DressRoomResponse.of(savedDressRoom);
            }
            // 하의만 있는 드레스룸이 존재하지 않는 경우
            else {
                // 하의를 조회
                Product bottom = productRepository.findById(productBottomId)
                    .orElseThrow(() -> new GoneException("존재하지 않는 하의입니다."));

                // 저장
                DressRoom dressRoom = DressRoom.builder()
                    .model(model)
                    .productTop(null)
                    .productBottom(bottom)
                    .member(member)
                    .build();

                DressRoom savedDressRoom = dressRoomRepository.save(dressRoom);

                // 캐시된 하의가 없는 경우
                requestAIServer(modelId, productTopId, productBottomId, savedDressRoom, null,
                    false);

                return DressRoomResponse.of(savedDressRoom);
            }
        }

        // 이외의 경우
        throw new BadRequestException("상의나 하의 중 하나는 선택해야 합니다.");
    }

    private void requestAIServer(Long modelId, Long productTopId, Long productBottomId,
        DressRoom savedDressRoom, Boolean topAlready, Boolean bottomAlready) {
        // AI 서버에 요청
        WebClient webClient = WebClient.builder()
            .baseUrl("http://222.107.238.75:8111")
            .build();

        DressRoomAIRequest request = DressRoomAIRequest.builder()
            .dressRoomId(savedDressRoom.getId())
            .modelId(modelId)
            .productTopId(productTopId)
            .topAlready(topAlready)
            .productBottomId(productBottomId)
            .bottomAlready(bottomAlready)
            .build();

        log.info("dressRoomId: {}", request.getDressRoomId());
        log.info("modelId: {}", request.getModelId());
        log.info("productTopId: {}", request.getProductTopId());
        log.info("topAlready: {}", request.getTopAlready());
        log.info("productBottomId: {}", request.getProductBottomId());
        log.info("bottomAlready: {}", request.getBottomAlready());

        webClient.post()
            .uri(uriBuilder -> uriBuilder.path("/api/fitmeai").build())
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                Mono.error(new InferenceFailureException("AI 서버 오류가 발생했습니다.")))
            .bodyToMono(byte[].class)
            .doOnNext(bytes -> {
                try {
                    if (bytes == null) {
                        log.info("byte is null");
                        throw new FileSaveException();
                    }

                    String filename = imgUrl;

                    // .images/dressroom 폴더가 없으면 생성
                    Path path = Paths.get(filename);
                    if (!Files.exists(path)) {
                        Files.createDirectories(path);
                    }

                    // 상의와 하의가 모두 있는 경우
                    if (productTopId != null && productBottomId != null) {
                        // sum 폴더가 없으면 생성
                        filename += "sum/";

                        path = Paths.get(filename);
                        if (!Files.exists(path)) {
                            Files.createDirectories(path);
                        }

                        filename += productTopId + "_" + productBottomId + ".jpg";
                    }
                    // 상의만 있는 경우
                    else if (productTopId != null) {
                        // top 폴더가 없으면 생성
                        filename += "top/";

                        path = Paths.get(filename);
                        if (!Files.exists(path)) {
                            Files.createDirectories(path);
                        }

                        filename += productTopId + ".jpg";
                    }
                    // 하의만 있는 경우
                    else if (productBottomId != null) {
                        // bottom 폴더가 없으면 생성
                        filename += "bottom/";

                        path = Paths.get(filename);
                        if (!Files.exists(path)) {
                            Files.createDirectories(path);
                        }

                        filename += productBottomId + ".jpg";
                    }

                    try (FileOutputStream fos = new FileOutputStream(filename)) {
                        fos.write(bytes);
                    } catch (IOException e) {
                        log.error("이미지 저장 중 오류 발생", e);
                        throw new FileSaveException();
                    }
                } catch (IOException e) {
                    log.error("폴더 생성 중 중 오류 발생", e);
                    throw new FileSaveException();
                }
            })
            .block();
    }

    // DressRoom 삭제
    @Transactional
    public void deleteDressRoom(Long memberId, Long dressRoomId) {
        DressRoom dressRoom = dressRoomRepository.findByIdAndMemberId(memberId, dressRoomId)
            .orElseThrow(() -> new GoneException("존재하지 않는 드레스룸입니다."));

        dressRoom.deleteDressRoom();

        dressRoomRepository.save(dressRoom);
    }
}
