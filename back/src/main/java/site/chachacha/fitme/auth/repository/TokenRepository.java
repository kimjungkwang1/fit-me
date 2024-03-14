package site.chachacha.fitme.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.chachacha.fitme.auth.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
