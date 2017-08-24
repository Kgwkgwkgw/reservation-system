package naverest.reservation.controller.user;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

import naverest.reservation.annotation.LogginedUser;
import naverest.reservation.domain.User;
import naverest.reservation.service.UserService;
import naverest.reservation.service.impl.LoginServiceImpl;

@Controller
@RequestMapping("/login")
public class LoginController {
	private UserService userService;
	private LoginServiceImpl loginServiceImpl;
	@Value("${naverest.userDir}")
	private String DIR_NAME;

	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	private final static String SESSION_STATE = "oauthState";

	@Autowired
	public LoginController(UserService userService, LoginServiceImpl loginServiceImpl) {
		this.userService = userService;
		this.loginServiceImpl = loginServiceImpl;
	}

	@GetMapping
	public String loginGateway() {
		return DIR_NAME + "/loginGateway";
	}

	@GetMapping("/{sns:[google|naver|facebook]+}")
	public String login(HttpSession session, @RequestParam(required = false, defaultValue = "/") String returnUrl,
			@PathVariable String sns) throws IOException {
		String oauthState = generateRandomString();
		session.setAttribute(SESSION_STATE, oauthState);
		
		String authUrl = loginServiceImpl.getAuthorizationUrl(sns, oauthState, returnUrl);
		System.out.println(authUrl);
		return "redirect:" + authUrl;
	}

	@GetMapping("/{sns:[google|naver|facebook]+}/callback")
	public String callback(@RequestParam String code, @RequestParam String state,
			@RequestParam(required = false, defaultValue = "/") String returnUrl,
			@RequestParam(required = false) String error, HttpSession session, @PathVariable String sns)
			throws IOException {
		
		if (error == null) {
			OAuth2AccessToken oauthToken = loginServiceImpl.getAccessToken(sns, (String) session.getAttribute(SESSION_STATE), code, state);
			session.setAttribute("oauthToken", oauthToken);
			session.setAttribute("oauthTokenExpires", getExpireDate());
			
			User user = loginServiceImpl.getUser(sns, oauthToken);
			
			session.setAttribute("loginInfo", userService.login(user));

			return "redirect:" + returnUrl;

		} else {
			log.debug(error);
			return "error";
		}
	}

	@GetMapping("/refresh")
	public String reqRefreshAccessTocken(HttpSession session,
			@RequestParam(required = false, defaultValue = "/") String returnUrl,
			@LogginedUser User user) throws IOException {
		System.out.println(user);
		String sns = user.getSnsType();
		OAuth2AccessToken oauthToken = (OAuth2AccessToken) session.getAttribute("oauthToken");

		if (oauthToken.getRefreshToken() == null) {
			return "redirect:/login/" + sns;
		}
		OAuth2AccessToken newAccessToken = loginServiceImpl.getRefreshedAccessToken(sns, oauthToken);

		session.setAttribute("oauthToken", newAccessToken);
		session.setAttribute("oauthTokenExpires", getExpireDate());
		return "redirect:" + returnUrl;
	}

	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}

	private Date getExpireDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		return cal.getTime();
	}
}
