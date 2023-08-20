package LoginEx.com.ex.models.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_verification")
public class EmailVerificationEntity {

	@Id
	@Column(name ="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "verification_code")
	private String verificationCode;
	
	@Column(name ="created_at")
	private Timestamp createdAt;

	public EmailVerificationEntity() {
		
	}

	public EmailVerificationEntity(long id, String email, String verificationCode, Timestamp createdAt) {
		this.id = id;
		this.email = email;
		this.verificationCode = verificationCode;
		this.createdAt = createdAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	
}
