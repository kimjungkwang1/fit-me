package site.chachacha.fitme.domain.dressroom;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.common.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class DressRoom extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    Long id;

    private String imageUrl;
}
