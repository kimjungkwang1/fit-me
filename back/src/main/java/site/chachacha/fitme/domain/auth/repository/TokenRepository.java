package site.chachacha.fitme.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.chachacha.fitme.domain.auth.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
