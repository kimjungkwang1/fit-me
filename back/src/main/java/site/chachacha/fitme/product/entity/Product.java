package site.chachacha.fitme.product.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import site.chachacha.fitme.brand.entity.Brand;
import site.chachacha.fitme.category.entity.Category;
import site.chachacha.fitme.entity.BaseEntity;

@Getter
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "brand_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;  // MALE = 0, FEMALE = 1, UNISEX = 2

    private String ageRange;

    private Integer price;

    private String mainImageUrl;

    private String detailImageUrl;
}
