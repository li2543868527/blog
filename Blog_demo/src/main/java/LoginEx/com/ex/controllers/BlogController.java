package LoginEx.com.ex.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import LoginEx.com.ex.models.entity.BlogEntity;
import LoginEx.com.ex.models.entity.UserEntity;
import LoginEx.com.ex.services.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession httpSession;

	@GetMapping("/index")
	public String getBlogIndex(Model model) {
		UserEntity user = (UserEntity) httpSession.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			List<BlogEntity> list = blogService.selectAll(user.getuserId());
			model.addAttribute("list", list);
			model.addAttribute("userName", user.getuserName());

			return "index.html";
		}
	}

	@GetMapping("/index/register")
	public String getBlogRegisterPage(Model model) {
		UserEntity user = (UserEntity) httpSession.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("userName", user.getuserName());
			model.addAttribute("userId", user.getuserId());
			return "blog_register.html";
		}
	}

	@PostMapping("/index/register/process")
	public String getBlogRegisterProcess(@RequestParam String blogTitle, @RequestParam LocalDate registerDate,
			@RequestParam MultipartFile blogImage, @RequestParam String blogDetails, @RequestParam Long userId,
			Model model) {
		UserEntity user = (UserEntity) httpSession.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/temp/blog-img/" + fileName));
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (blogService.createBlog(blogTitle, registerDate, fileName, blogDetails,userId)) {
				return "redirect:/index";
			} else {
				return "redirect:/index/register";
			}
		}
	}

}
