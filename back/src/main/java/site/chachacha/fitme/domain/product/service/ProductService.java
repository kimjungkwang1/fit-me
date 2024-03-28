package site.chachacha.fitme.domain.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.domain.like.repository.ProductLikeRepository;
import site.chachacha.fitme.domain.product.dto.ProductDetailResponse;
import site.chachacha.fitme.domain.product.dto.ProductOptionResponse;
import site.chachacha.fitme.domain.product.dto.ProductRankingListResponse;
import site.chachacha.fitme.domain.product.dto.ProductRankingResponse;
import site.chachacha.fitme.domain.product.dto.ProductResponse;
import site.chachacha.fitme.domain.product.dto.ProductSearchRequest;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;
import site.chachacha.fitme.domain.product.exception.ProductNotFoundException;
import site.chachacha.fitme.domain.product.repository.ProductCustomRepository;
import site.chachacha.fitme.domain.product.repository.ProductOptionRepository;
import site.chachacha.fitme.domain.product.repository.ProductRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String RANKING_NAME = "product:rankings";
    private static final String RANKING_UPDATE_TIME_NAME = "updateTime";
    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;
    private final ProductLikeRepository productLikeRepository;
    private final ProductOptionRepository productOptionRepository;

    // 실시간 상품 랭킹 조회
    public ProductRankingListResponse getProductRankings(int lastRank, int size) {

        String rankingsJson = redisTemplate.opsForValue().get(RANKING_NAME);
        List<ProductRankingResponse> allRankings = new ArrayList<>();

        if (rankingsJson != null) {
            try {
                // JSON 문자열을 ProductRankingResponse 리스트로 역직렬화
                allRankings = new ObjectMapper().readValue(rankingsJson, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException("상품 랭킹 JsonProcessingException ", e);
            }
        }

        // 요청된 범위에 맞게 랭킹 리스트를 자름
        int endIndex = Math.min(lastRank + size, allRankings.size());
        List<ProductRankingResponse> rankings = allRankings.subList(lastRank, endIndex);

        String updateTime = redisTemplate.opsForValue().get(RANKING_UPDATE_TIME_NAME);
        return new ProductRankingListResponse(rankings, updateTime);
    }

    // 상품 목록 조회
    public List<ProductResponse> getProducts(ProductSearchRequest request) {
        List<Product> products = productCustomRepository.findAllByProductConditions(
            request.getLastId(), request.getLastPopularityScore(), request.getSize(), request.getKeyword(), request.getAgeRanges(),
            request.getBrandIds(), request.getCategoryIds(), request.getStartPrice(), request.getEndPrice(), request.getSortBy());
        return products
            .stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
    }

    // 상품 상세 조회
    public ProductDetailResponse getProduct(Long productId, Long memberId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        boolean liked = productLikeRepository.existsByProductAndMemberId(product, memberId);
        return ProductDetailResponse.of(product, liked);
    }

    // 상품 옵션 목록 조회
    public List<ProductOptionResponse> getProductOptions(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        List<ProductOption> productOptions = productOptionRepository.findAllByProduct(product);
        return productOptions.stream().map(ProductOptionResponse::from).toList();
    }
}
