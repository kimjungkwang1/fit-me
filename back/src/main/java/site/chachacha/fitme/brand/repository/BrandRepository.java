package site.chachacha.fitme.brand.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.brand.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
