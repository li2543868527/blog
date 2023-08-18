package LoginEx.com.ex.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import LoginEx.com.ex.models.entity.BlogEntity;

public interface BlogDao extends JpaRepository<BlogEntity, Long>{
	
	//saveメソッドの意味は保存することです。
	BlogEntity save(BlogEntity blogEntity);
	
	//データベースを探して、すべてのブログをリストで受けます。
	List<BlogEntity> findAll();
	
	//ブログのIDでブログを探します
	BlogEntity findByBlogId(Long blogId);
	
	//ブログのTITLEでブログを探します
	BlogEntity findByBlogTitle(String blogTitle);
	
	//ブログTITLEと詳細にキーワードで探します。
	//containingの意味はfuzzy search＆あいまい検索＆模糊查找の意味です。キーワードがあれば探せます。
	List<BlogEntity> findByBlogTitleContainingOrBlogDetailsContaining(String titleKeyword,String detailsKeyword);
}
