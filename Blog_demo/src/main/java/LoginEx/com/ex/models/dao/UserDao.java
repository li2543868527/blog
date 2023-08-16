package LoginEx.com.ex.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import LoginEx.com.ex.models.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Long> {
	//データを保存するinsert文に該当するメソッドを書く
	
	//UserEntityを引数として受け取ってUserEntityの内容を保存して保存した結果を返す
	UserEntity save(UserEntity userEntity);
	
	//userNameの内容を受け取ってUserEntityを返すメソッド
	//WHERE user_name =?
	UserEntity findByUserName(String userName);
	
	//SELECT * FROM users WHERE user_name=?AND password=?
	UserEntity findByEmailAndPassword(String email,String password);
	
	//一覧取得 SELECT * FROM users
	List<UserEntity> findAll();
	
}
