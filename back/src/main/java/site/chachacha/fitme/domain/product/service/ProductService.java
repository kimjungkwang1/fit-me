package site.chachacha.fitme.domain.product.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.domain.like.repository.ProductLikeRepository;
import site.chachacha.fitme.domain.product.dto.ProductDetailResponse;
import site.chachacha.fitme.domain.product.dto.ProductResponse;
import site.chachacha.fitme.domain.product.dto.ProductSearchRequest;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;
import site.chachacha.fitme.domain.product.exception.ProductNotFoundException;
import site.chachacha.fitme.domain.product.repository.ProductCustomRepository;
import site.chachacha.fitme.domain.product.repository.ProductRepository;
import site.chachacha.fitme.domain.product.dto.ProductOptionResponse;
import site.chachacha.fitme.domain.product.repository.ProductOptionRepository;
import site.chachacha.fitme.domain.review.entity.ProductReview;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;
    private final ProductLikeRepository productLikeRepository;
    private final ProductOptionRepository productOptionRepository;

    public List<ProductResponse> getProducts(ProductSearchRequest request) {

        List<Product> products = productCustomRepository.findAllByProductConditions(
            request.getLastId(), request.getSize(), request.getGender(), request.getAgeRange(), request.getBrandIds(), request.getCategoryIds(),
            request.getStartPrice(), request.getEndPrice(), request.getSortBy());

        return products
            .stream()
            .map(product -> {
                Integer reviewCount = product.getProductReviews().size();
                Double reviewRating = calculateReviewRating(product.getProductReviews());
                return ProductResponse.of(product, reviewRating, reviewCount);
            })
            .collect(Collectors.toList());
    }

    public ProductDetailResponse getProduct(Long productId, Long memberId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        boolean liked = productLikeRepository.existsByProductAndMemberId(product, memberId);
        Integer reviewCount = product.getProductReviews().size();
        Double reviewRating = calculateReviewRating(product.getProductReviews());
        return ProductDetailResponse.of(product, liked, reviewRating, reviewCount);
    }

    public List<ProductOptionResponse> getProductOptions(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        List<ProductOption> productOptions = productOptionRepository.findAllByProduct(product);
        return productOptions.stream().map(ProductOptionResponse::from).toList();
    }

//    public ProductRankingListResponse getProductRanking() {
//        return null;
//    }

    private double calculateReviewRating(List<ProductReview> productReviews) {
        if (productReviews.isEmpty()) {
            return 0.0;
        }

        double averageScore = productReviews.stream()
            .mapToInt(ProductReview::getScore)
            .average()
            .orElse(0.0);

        return Math.round(averageScore * 10.0) / 10.0; // 평균 점수를 반올림하여 반환
    }
}
