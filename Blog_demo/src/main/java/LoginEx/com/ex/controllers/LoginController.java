package LoginEx.com.ex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import LoginEx.com.ex.models.entity.UserEntity;
import LoginEx.com.ex.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;

	@GetMapping("/login")
	public ModelAndView getLoginPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login.html");
		return mav;
	}

	@PostMapping("/login/process")
	public String login(@RequestParam String email, @RequestParam String password) {
		UserEntity user = userService.loginCheck(email, password);
		if (user ==null) {
			return "redirect:/login";
		} else {
			httpSession.setAttribute("user", user);
			return "redirect:/index";
		}
	}
	
	@GetMapping("/logout")
	public String logout() {
		//セッションの無効化
		httpSession.invalidate();
		return "redirect:/login";
	}

}
