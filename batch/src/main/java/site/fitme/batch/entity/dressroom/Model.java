package site.fitme.batch.entity.dressroom;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Model {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    private String url;

    @Builder
    private Model(String url) {
        this.url = url;
    }
}
