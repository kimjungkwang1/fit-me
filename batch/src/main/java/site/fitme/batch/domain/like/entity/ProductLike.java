package site.fitme.batch.domain.like.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.fitme.batch.entity.common.BaseEntity;
import site.fitme.batch.domain.member.entity.Member;
import site.fitme.batch.domain.product.entity.Product;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public ProductLike(Product product, Member member) {
        this.product = product;
        this.member = member;
    }
}
