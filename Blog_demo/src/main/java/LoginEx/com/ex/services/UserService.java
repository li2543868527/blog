package LoginEx.com.ex.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LoginEx.com.ex.models.dao.UserDao;
import LoginEx.com.ex.models.entity.UserEntity;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	// 登録処理のメソッド
	public boolean createUser(String email, String userName, String password) {
		if (userDao.findByUserName(userName) == null) {
			//timestampで時間のオブジェクトを新規します
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			//保存処理
			userDao.save(new UserEntity(email, userName, password,currentTime));
			return true;
		} else {
			return false;
		}
	}

	// ログインチェック用のメソッド
	public UserEntity loginCheck(String email, String password) {
		if (userDao.findByEmailAndPassword(email, password) == null) {
			return null;
		}
		return userDao.findByEmailAndPassword(email, password);
	}
	
	
	
}
