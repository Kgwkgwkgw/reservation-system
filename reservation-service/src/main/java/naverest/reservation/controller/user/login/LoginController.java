package naverest.reservation.controller.user.login;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

import naverest.reservation.domain.User;
import naverest.reservation.dto.NaverLoginProfile;
import naverest.reservation.oauth.naver.NaverLoginApiService;
import naverest.reservation.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	/* NaverLoginBO */
	private NaverLoginApiService naverLoginApiService;
	private UserService userService;
	private final static String SESSION_STATE = "oauthState";

	@Autowired
	public LoginController(NaverLoginApiService naverLoginApiService, UserService userService) {
		this.naverLoginApiService = naverLoginApiService;
		this.userService = userService;
	}

	@GetMapping
	public String login(HttpSession session, @RequestParam String returnUrl) {
		// 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출
		String oauthState = generateRandomString();
		session.setAttribute(SESSION_STATE, oauthState);
		String naverAuthUrl = naverLoginApiService.getAuthorizationUrl(oauthState, returnUrl);

		// 생성한 인증 URL을 View로 전달
		return "redirect:" + naverAuthUrl;
	}

	// 네이버 로그인 성공시 callback호출 메소드
	@GetMapping("/callback")
	public String callback(@RequestParam String code, @RequestParam String state, @RequestParam String returnUrl,
			@RequestParam(required = false) String error, HttpSession session) throws IOException {
		if (error == null) {
			OAuth2AccessToken oauthToken;
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			oauthToken = naverLoginApiService.reqAccessToken((String) session.getAttribute(SESSION_STATE), code, state);
			session.setAttribute("oauthToken", oauthToken);

			cal.add(Calendar.HOUR_OF_DAY, 1);
			session.setAttribute("oauthTokenExpires", cal.getTime());

			NaverLoginProfile naverLoginProfile = naverLoginApiService.getUserProfile(oauthToken);

			User user = userService.login(naverLoginProfile);

			session.setAttribute("loginInfo", user);
			// 네이버 로그인 성공 페이지 View 호출
			return "redirect:" + returnUrl;
		} else
			return "error";
	}

	// 세션 유효성 검증을 위한 난수 생성기
	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}

}
