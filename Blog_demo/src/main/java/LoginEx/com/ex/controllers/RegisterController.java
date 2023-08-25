package LoginEx.com.ex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import LoginEx.com.ex.models.entity.EmailVerificationEntity;
import LoginEx.com.ex.services.EmailVerificationService;
import LoginEx.com.ex.services.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@Autowired
    private EmailVerificationService emailVerificationService;
	
	

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

		if(userService.createUser(email, userName, password)) {
			mav.setViewName("login.html");
			return mav;
		}else {
			mav.setViewName("register.html");
			return mav;
		}

	}
	
    @PostMapping("/register/sendCode")
    @ResponseBody
    public String sendVerificationCode(@RequestParam String email) {
        // 生成验证码并发送邮件
        try {
            EmailVerificationEntity verificationEntity = emailVerificationService.generateVerificationCode(email);
            emailVerificationService.sendVerificationCodeEmail(email, verificationEntity.getVerificationCode());
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
	
	
	
	

}
