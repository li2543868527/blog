package LoginEx.com.ex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import LoginEx.com.ex.services.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService adminService;

	//普通のゲット・ポストメソッド
	@GetMapping("/register")
	public ModelAndView getRegisterPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("register.html");
		return mav;
	}

	@PostMapping("/register/process")
	public ModelAndView register(@RequestParam String userName, @RequestParam String password,
			@RequestParam String email) {
		// もし、usersテーブルないに登録した名前が存在しなかった場合、テーブルに保存処理をする。
		ModelAndView mav = new ModelAndView();

		if(adminService.createUser(email, userName, password)) {
			mav.setViewName("login.html");
			return mav;
		}else {
			mav.setViewName("register.html");
			return mav;
		}

	}

}
