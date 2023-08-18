package LoginEx.com.ex.models.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//これは、ユーザーのentityです。

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;

	@Column(name = "email")
	private String email;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;
	
	@Column(name = "register_date")
	private Timestamp registerDate;

	public UserEntity() {
	}

	public UserEntity(String email, String userName, String password,Timestamp registerDate) {
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.registerDate = registerDate;
	}

	public long getuserId() {
		return userId;
	}

	public void setuserId(long userId) {
		this.userId = userId;
	}

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public String getuserName() {
		return userName;
	}

	public void setuserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "userEntity [userId=" + userId + ", email=" + email + ", userName=" + userName
				+ ", password=" + password + "]";
	}
	
	

}
