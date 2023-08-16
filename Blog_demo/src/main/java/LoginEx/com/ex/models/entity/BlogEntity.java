package LoginEx.com.ex.models.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blogs")
public class BlogEntity {

	@Id
	@Column(name = "blog_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long blogId;

	@Column(name = "blog_title")
	private String blogTitle;

	@Column(name = "register_date")
	private LocalDate registerDate;

	@Column(name = "blog_image")
	private String blogImage;

	@Column(name = "blog_details")
	private String blogDetails;

	@Column(name = "user_id")
	private Long userId;

	public BlogEntity() {

	}

	public BlogEntity(String blogTitle, LocalDate registerDate, String blogImage, String blogDetails, Long userId) {
		this.blogTitle = blogTitle;
		this.registerDate = registerDate;
		this.blogImage = blogImage;
		this.blogDetails = blogDetails;
		this.userId = userId;
	}

	public BlogEntity(long blogId, String blogTitle, LocalDate registerDate, String blogImage, String blogDetails,
			Long userId) {
		this.blogId = blogId;
		this.blogTitle = blogTitle;
		this.registerDate = registerDate;
		this.blogImage = blogImage;
		this.blogDetails = blogDetails;
		this.userId = userId;
	}

	public long getBlogId() {
		return blogId;
	}

	public void setBlogId(long blogId) {
		this.blogId = blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public String getBlogImage() {
		return blogImage;
	}

	public void setBlogImage(String blogImage) {
		this.blogImage = blogImage;
	}

	public String getBlogDetails() {
		return blogDetails;
	}

	public void setBlogDetails(String blogDetails) {
		this.blogDetails = blogDetails;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
