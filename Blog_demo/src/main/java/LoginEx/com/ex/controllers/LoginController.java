package LoginEx.com.ex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import LoginEx.com.ex.services.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public ModelAndView getLoginPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login.html");
		return mav;
	}

	@PostMapping("/login/process")
	public ModelAndView login(@RequestParam String email, @RequestParam String password) {
		ModelAndView mav = new ModelAndView();
		if (userService.loginCheck(email, password)) {
			
			mav.addObject("email", email);
			mav.addObject("password", password);
			
			mav.setViewName("result.html");
			
			return mav;
		} else {
			mav.setViewName("login.html");
			return mav;
		}

	}

}
