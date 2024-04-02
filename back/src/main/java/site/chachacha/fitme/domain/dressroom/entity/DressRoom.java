package site.chachacha.fitme.domain.dressroom.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.common.entity.BaseEntity;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.product.entity.Product;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class DressRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    // 최종 결과물 이미지 URL
    private String imageUrl;

    @NotNull
    @JoinColumn(name = "model_id")
    @ManyToOne(fetch = LAZY)
    private Model model;

    @JoinColumn(name = "product_id_top")
    @ManyToOne(fetch = LAZY)
    private Product productTop;

    @JoinColumn(name = "product_id_bottom")
    @ManyToOne(fetch = LAZY)
    private Product productBottom;

    @NotNull
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    private DressRoom(Model model, Product productTop, Product productBottom, Member member) {
        // 상의, 하의 둘 다 입힌 경우
        if (productTop.getId() != null && productBottom.getId() != null) {
            this.imageUrl =
                "https://fit-me.site/images/dressroom/men/sum/" + productTop.getId() + "_"
                    + productBottom.getId() + ".jpg";
        }
        // 상의만 입힌 경우
        else if (productTop.getId() != null) {
            this.imageUrl =
                "https://fit-me.site/images/dressroom/men/top/" + productTop.getId() + ".jpg";
        }
        // 하의만 입힌 경우
        else if (productBottom.getId() != null) {
            this.imageUrl =
                "https://fit-me.site/images/dressroom/men/bottom/" + productBottom.getId() + ".jpg";
        }

        this.model = model;
        this.productTop = productTop;
        this.productBottom = productBottom;
        this.member = member;
        this.member.addDressRoom(this);
    }

    // == 비즈니스 로직 == //
    public void deleteDressRoom() {
        this.member = null;
    }
}
