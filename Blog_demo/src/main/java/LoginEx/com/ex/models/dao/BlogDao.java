package LoginEx.com.ex.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import LoginEx.com.ex.models.entity.BlogEntity;

public interface BlogDao extends JpaRepository<BlogEntity, Long>{
	BlogEntity save(BlogEntity blogEntity);
	
	List<BlogEntity> findAll();
	
	BlogEntity findByBlogId(Long blogId);
	
	BlogEntity findByBlogTitle(String blogTitle);
	
}
