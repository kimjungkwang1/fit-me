package site.chachacha.fitme.domain.dressroom.service;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
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

        Member member = memberRepository.findNotDeletedById(memberId)
            .orElseThrow(() -> new GoneException("존재하지 않는 회원입니다."));

        Model model = modelRepository.findById(modelId)
            .orElseThrow(() -> new GoneException("존재하지 않는 모델입니다."));

        Product top = null;
        if (productTopId != null) {
            top = productRepository.findById(productTopId)
                .orElseThrow(() -> new GoneException("존재하지 않는 상의입니다."));
        }

        Product bottom = null;
        if (productBottomId != null) {
            bottom = productRepository.findById(productBottomId)
                .orElseThrow(() -> new GoneException("존재하지 않는 하의입니다."));
        }

        // 캐싱
        // DressRoom Entity 중에 같은 Model, ProductTop, ProductBottom이 있는지 확인
        List<DressRoom> existingDressRoom = dressRoomRepository.findByModelAndProductTopAndProductBottom(
            modelId, productTopId, productBottomId);

        // 존재하지 않으면 새로운 이미지 생성
        if (existingDressRoom.isEmpty()) {
            Boolean topAlready = null;
            Boolean bottomAlready = null;

            // productTopId, productBottomId 둘 다 있는 경우
            if (productTopId != null && productBottomId != null) {
                topAlready = dressRoomRepository.findByProductTopAndNull(productTopId);
                bottomAlready = dressRoomRepository.findByProductBottomAndNull(productBottomId);
            }
            // productTopId가 있는 경우
            else if (productTopId != null) {
                topAlready = dressRoomRepository.findByProductTopAndNull(productTopId);
            }
            // productBottomId가 있는 경우
            else if (productBottomId != null) {
                bottomAlready = dressRoomRepository.findByProductBottomAndNull(productBottomId);
            }

            // AI 서버에 요청
            WebClient webClient = WebClient.builder()
                .baseUrl("http://222.107.238.75:8111")
                .build();

            DressRoomAIRequest request = DressRoomAIRequest.builder()
                .modelId(modelId)
                .productTopId(productTopId)
                .topAlready(topAlready)
                .productBottomId(productBottomId)
                .bottomAlready(bottomAlready)
                .build();

            webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/fitmeai").build())
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                    Mono.error(new InferenceFailureException("AI 서버 오류가 발생했습니다.")))
                .bodyToMono(Resource.class) // Resource 타입으로 파일 데이터를 받음
                .doOnNext(resource -> {
                    try {
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

                        // 파일 저장 로직
                        path = Paths.get(filename);
                        Files.copy(resource.getInputStream(), path,
                            StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        log.error("파일 저장 중 오류 발생", e);
                        throw new FileSaveException();
                    }
                })
                .block();
        }

        DressRoom dressRoom = DressRoom.builder()
            .model(model)
            .productTop(top)
            .productBottom(bottom)
            .member(member)
            .build();

        DressRoom savedDressRoom = dressRoomRepository.save(dressRoom);

        return DressRoomResponse.of(savedDressRoom);
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
