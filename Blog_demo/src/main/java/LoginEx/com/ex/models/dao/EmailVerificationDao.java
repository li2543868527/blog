package LoginEx.com.ex.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import LoginEx.com.ex.models.entity.EmailVerificationEntity;

public interface EmailVerificationDao extends JpaRepository<EmailVerificationEntity, Long>{
	EmailVerificationEntity findByEmail(String email);
}
