package site.chachacha.fitme.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.chachacha.fitme.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
