package site.chachacha.fitme.domain.dressroom.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.chachacha.fitme.domain.dressroom.entity.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
