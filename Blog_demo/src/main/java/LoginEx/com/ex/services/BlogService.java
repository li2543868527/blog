package LoginEx.com.ex.services;

import java.time.LocalDate;

import java.util.List;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LoginEx.com.ex.models.dao.BlogDao;
import LoginEx.com.ex.models.entity.BlogEntity;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;

	//このメソッドはすべてのブログを取り出す。でも、もしブログのIDが存在しなければ、何も取り出せません、NULLを返します
	public List<BlogEntity> selectAll(Long blogId) {
		if (blogId == null) {
			return null;
		}
		return blogDao.findAll();
	}

	//新規ブログのロジックです。もしタイトルが存在する場合、失敗しましたから、falseを返します。
	//存在しなければ、新規ブログをします
	public boolean createBlog(String blogTitle, LocalDate registerDate, String blogImage, String blogDetails,
			Long userId) {
		BlogEntity list = blogDao.findByBlogTitle(blogTitle);
		if (list == null) {
			blogDao.save(new BlogEntity(blogTitle, registerDate, blogImage, blogDetails, userId));
			return true;
		}
		return false;
	}

	//もしブログのIDがなければ、NULLを返します。ある場合は、そのブログを返します。
	public BlogEntity getBlogPost(Long blogId) {
		if (blogId == null) {
			return null;
		}
		return blogDao.findByBlogId(blogId);
	}

	//編集ブログのロジックです
	//もしブログが存在しなかった場合は、失敗しました、false
	//存在する場合は、もちろん編集できます。編集して、saveメソッドでsaveします。
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

	//ブログを削除するメソッドです
	//存在しなければ、削除する必要がない、false
	//存在する場合は、削除する
	public boolean deleteBlog(Long blogId) {
		if (blogId == null) {
			return false;
		} else {
			blogDao.deleteById(blogId);
			return true;
		}
	}
	
	//詳細画面を表示するロジック
	public BlogEntity getBlogDetailsPage(Long blogId) {
	    return blogDao.findByBlogId(blogId);
	}
	
	//サーチのロジック、メソッド名は本当に長い、Daoさんに聞きましょう。
	public List<BlogEntity> searchBlog(String term){
		
		return blogDao.findByBlogTitleContainingOrBlogDetailsContaining(term, term);
	}
	


}
