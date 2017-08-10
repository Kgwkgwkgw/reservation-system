package naverest.reservation.controller.user.blog;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

import naverest.reservation.dto.NaverBlogResult;
import naverest.reservation.oauth.naver.NaverLoginApiService;

@Controller
@RequestMapping("/blog")
public class BlogController {
	@Value("${USER_DIR}")
	private String DIRNAME;
	private String SUBJECT ="/blog";
	private NaverLoginApiService naverLoginApiService;
	
	@Autowired
	public BlogController(NaverLoginApiService naverLoginApiService) {
		this.naverLoginApiService = naverLoginApiService;
	}
	@GetMapping("/form")
	public String form(HttpSession session, Model model) throws IOException {
		model.addAttribute("postUrl", "/blog");
		return DIRNAME+SUBJECT+"/form";
	}
	@PostMapping
	public String post(HttpSession session, Model model, @RequestParam String title,@RequestParam String contents) throws IOException {
		NaverBlogResult naverBlogResult = naverLoginApiService.postBlogContent((OAuth2AccessToken)session.getAttribute("oauthToken"), title, contents);
		if(naverBlogResult!=null) {
			model.addAttribute("naverBlogResult", naverBlogResult);
			return DIRNAME+SUBJECT+"/result";
		}
		return "error";
	}
}
