package site.fitme.batch.entity.product;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.fitme.batch.entity.common.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private double monthlyPopularityScore = 0;

    public void updateMonthlyPopularityScore(double monthlyPopularityScore) {
        this.monthlyPopularityScore = monthlyPopularityScore;
    }
}
