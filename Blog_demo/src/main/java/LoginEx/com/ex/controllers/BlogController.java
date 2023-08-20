package LoginEx.com.ex.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import LoginEx.com.ex.models.entity.BlogEntity;
import LoginEx.com.ex.models.entity.UserEntity;
import LoginEx.com.ex.services.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogController {

	//サービスの対象の導入
	@Autowired
	private BlogService blogService;

	//セッションの対象の導入
	@Autowired
	private HttpSession httpSession;

	// ホームページのインデックスのゲットメソッド
	@GetMapping("/index")
	public String getBlogIndex(Model model, HttpSession httpSession) {
	    // ダウンキャストをして、ユーザーの対象を取得
	    UserEntity user = (UserEntity) httpSession.getAttribute("user");

	    // もし、ユーザーがログインしていない場合は、未ログイン用のページにリダイレクト
	    if (user == null) {
	        return "redirect:/notloggedin";
	    } else {
	        // ログインしている場合、ブログをデータベースから取得して表示
	        List<BlogEntity> list = blogService.selectAll(user.getuserId());
	        list.sort(Comparator.comparing(BlogEntity::getRegisterDate).reversed());

	        model.addAttribute("list", list);
	        model.addAttribute("userName", user.getuserName());
	        return "index.html";
	    }
	}
	
	
	@GetMapping("/notloggedin")
	public String showNotLoggedInPage(Model model) {
		
		List<BlogEntity> list = blogService.previewAll();
        list.sort(Comparator.comparing(BlogEntity::getRegisterDate).reversed());

        model.addAttribute("list", list);
	    return "notloggedin.html"; 
	}

	
	
	
	
	// ブログの登録処理
	@GetMapping("/index/register")
	public String getBlogRegisterPage(Model model) {
		UserEntity user = (UserEntity) httpSession.getAttribute("user");
		//もし、ユーザーが存在しなかった場合あるいはログインしない場合は、もう一度ログイン画面に戻る
		if (user == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("userName", user.getuserName());
			model.addAttribute("userId", user.getuserId());
			//ブログのregisterに行く
			return "blog_register.html";
		}
	}
	
	@PostMapping("/index/register/process")
	public String getBlogRegisterProcess(@RequestParam String blogTitle, @RequestParam LocalDate registerDate,
			@RequestParam MultipartFile blogImage, @RequestParam String blogDetails, @RequestParam Long userId,
			Model model) {
		//ブログ登録必要なパラメーターを渡す
		UserEntity user = (UserEntity) httpSession.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			/**現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
			 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入**/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			/**ファイルを実際にサーバー上に保存するための処理を行っています。Files.copy()メソッドを使用して、
			 * productImageオブジェクトから入力ストリームを取得し、指定されたパスにファイルをコピー。
			 * Path.of()メソッドを使用して、保存先のパスを指定している。
			 * 保存先のパスは、"src/main/resources/static/blog-img/"というディレクトリの中に
			 * 、fileNameで指定されたファイル名で保存される。。**/
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/temp/blog-img/" + fileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//サービスのメソッドを呼び出して、ブログを保存する
			if (blogService.createBlog(blogTitle, registerDate, fileName, blogDetails, userId)) {
				return "redirect:/index";
			} else {
				//できなかった場合は、registerの画面に返す。
				return "redirect:/index/register";
			}
		}
	}

	
	
	
	// 詳細画面に飛び処理
	@GetMapping("/index/blog/{blogId}")
	public String getBlogDetails(@PathVariable Long blogId, Model model) {
		UserEntity user = (UserEntity) httpSession.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			//サービスのメソッドを呼び出して、それから、このブログをチェックします。もしなかった場合は、ホームページに戻ります。
			BlogEntity blog = blogService.getBlogDetailsPage(blogId);
			if (blog == null) {
				return "redirect:/index";
			}
			//もしある場合はブログ詳細画面に飛びます。
			model.addAttribute("blog", blog);
			model.addAttribute("userName", user.getuserName());
			model.addAttribute("user", user);
			model.addAttribute("userId", user.getuserId());
			model.addAttribute("userIdOfBlog", blog.getUserId());
			model.addAttribute("defaultUsername", user.getuserName());
			return "blog_detail_thing.html";
		}
	}

	// 編集画面処理
	@GetMapping("/index/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		UserEntity user = (UserEntity) httpSession.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			BlogEntity blog = blogService.getBlogPost(blogId);
			//びっくりマックの後ろの内容はこのブログを見るユーザーと新規ブログをしたユーザーは同一の人かを判断する。
			if (blog == null || !blog.getUserId().equals(user.getuserId())) {
				return "redirect:/index";
			}
			model.addAttribute("blog", blog);
			model.addAttribute("userName", user.getuserName());
			model.addAttribute("userId", user.getuserId());
			model.addAttribute("userIdOfBlog", blog.getUserId());
			model.addAttribute("defaultUsername", user.getuserName());
			return "blog_edit.html";
		}
	}

	@PostMapping("/index/edit/{blogId}/process")
	public String postBlogEditProcess(@PathVariable Long blogId, @RequestParam String blogTitle,
			@RequestParam LocalDate registerDate, @RequestParam MultipartFile blogImage,
			@RequestParam String blogDetails, Model model) {
		UserEntity user = (UserEntity) httpSession.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			/**現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
			 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入**/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			/**ファイルを実際にサーバー上に保存するための処理を行っています。Files.copy()メソッドを使用して、
			 * productImageオブジェクトから入力ストリームを取得し、指定されたパスにファイルをコピー。
			 * Path.of()メソッドを使用して、保存先のパスを指定している。
			 * 保存先のパスは、"src/main/resources/static/blog-img/"というディレクトリの中に
			 * 、fileNameで指定されたファイル名で保存される。。**/
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/temp/blog-img/" + fileName));
			} catch (Exception e) {
				e.printStackTrace();
			}

			//編集のロジックと新規ブログのロジックとほぼ同じです。新規ブログのコメントを参照してください。
			if (blogService.editBlog(blogId, blogTitle, registerDate, fileName, blogDetails)) {
				return "redirect:/index";
			} else {
				return "redirect:/index/edit/" + blogId;
			}
		}
	}
	
	//deleteのメソッドです
	@PostMapping("/index/delete/{blogId}")
	public String delete(@PathVariable Long blogId) {
		//サービスのメソッドを呼び出して、削除処理をする
		if(blogService.deleteBlog(blogId)) {
			return "redirect:/index";
		}else {
			//削除できなかった場合は編集画面に戻ります
			return "redirect:/index/edit/"+blogId;
		}
	}

	
	//search barのロジック
	@GetMapping("/index/search")
	public String searchBlog(@RequestParam String term, Model model) {
		//サービスの中のサーチメソッドを呼び出して、リストを受けます。
		List<BlogEntity> searchResults = blogService.searchBlog(term);
		//あるの場合なら（空きではない）、リストの内容を表示します。ないの場合なら、エラー画面に飛びます。
		if (!searchResults.isEmpty()) {
			model.addAttribute("searchResults", searchResults);
			return "search_result.html";
		}
		return "error.html";
	}
	
	//自己紹介の画面
	@GetMapping("/author")
	public String introductionMyself() {
		return "introduce.html";
	}

	
}
