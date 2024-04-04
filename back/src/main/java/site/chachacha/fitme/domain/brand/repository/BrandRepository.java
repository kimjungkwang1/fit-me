package site.chachacha.fitme.domain.brand.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.domain.brand.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
