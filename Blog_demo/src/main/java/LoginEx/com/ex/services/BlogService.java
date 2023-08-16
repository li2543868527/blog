package LoginEx.com.ex.services;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LoginEx.com.ex.models.dao.BlogDao;
import LoginEx.com.ex.models.entity.BlogEntity;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;

	public List<BlogEntity> selectAll(Long blogId) {
		if (blogId == null) {
			return null;
		}
		return blogDao.findAll();
	}

	public boolean createBlog(String blogTitle, LocalDate registerDate, String blogImage, String blogDetails,
			Long userId) {
		BlogEntity list = blogDao.findByBlogTitle(blogTitle);
		if (list == null) {
			blogDao.save(new BlogEntity(blogTitle, registerDate, blogImage, blogDetails, userId));
			return true;
		}
		return false;
	}

	public BlogEntity getBlogPost(Long blogId) {
		if (blogId == null) {
			return null;
		}
		return blogDao.findByBlogId(blogId);
	}

	public boolean editBlog(long blogId, String blogTitle, LocalDate registerDate, String blogImage,
			String blogDetails) {
		BlogEntity list = blogDao.findByBlogId(blogId);
		if (list == null) {
			return false;
		} else {
			list.setBlogTitle(blogTitle);
			list.setRegisterDate(registerDate);
			list.setBlogImage(blogImage);
			list.setBlogDetails(blogDetails);
			blogDao.save(list);
			return true;
		}

	}

	public boolean deleteBlog(Long blogId) {
		if (blogId == null) {
			return false;
		} else {
			blogDao.deleteById(blogId);
			return true;
		}
	}

}
