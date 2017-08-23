package naverest.reservation.controller.user;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

import naverest.reservation.domain.User;
import naverest.reservation.service.UserService;
import naverest.reservation.service.impl.GoogleLoginServiceImpl;



@Controller
@RequestMapping("/login")
public class LoginController {
	private UserService userService;
	private GoogleLoginServiceImpl loginService;
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	private final static String SESSION_STATE = "oauthState";

	@Autowired
	public LoginController(UserService userService, GoogleLoginServiceImpl loginService) {
		this.userService = userService;
		this.loginService = loginService;
	}
	
	@GetMapping
	public String login(HttpSession session, @RequestParam(required=false, defaultValue="/") String returnUrl) {
		String oauthState = generateRandomString();
		session.setAttribute(SESSION_STATE, oauthState);
		String naverAuthUrl = loginService.getAuthorizationUrl(oauthState, returnUrl);

		return "redirect:" + naverAuthUrl;
	}

	@GetMapping("/callback")
	public String callback(@RequestParam String code, @RequestParam String state, @RequestParam(required=false, defaultValue="/") String returnUrl,
			@RequestParam(required = false) String error, HttpSession session) throws IOException {
		System.out.println("calback 호출!!");
		OAuth2AccessToken oauthToken =loginService.reqAccessToken((String) session.getAttribute(SESSION_STATE), code, state);
		session.setAttribute("oauthToken", oauthToken);
		System.out.println(oauthToken.toString());
		session.setAttribute("oauthTokenExpires", getExpireDate());
		
		System.out.println(oauthToken.toString());
		User user = loginService.getUserProfile(oauthToken);
		session.setAttribute("loginInfo", userService.login(user));
		
	    
		if (error == null) {
			System.out.println("error는 null");
//			OAuth2AccessToken oauthToken;
			
//			oauthToken = loginService.reqAccessToken((String) session.getAttribute(SESSION_STATE), code, state);
//			session.setAttribute("oauthToken", oauthToken);
//			session.setAttribute("oauthTokenExpires", getExpireDate());
//
//			NaverLoginProfile naverLoginProfile = loginService.getUserProfile(oauthToken);
//
//			User user = userService.login(naverLoginProfile);
//
//			session.setAttribute("loginInfo", user);
			return "redirect:" + returnUrl;
		} else {
			log.debug(error);
			return "error";
		}
	}
	
	@GetMapping("/refresh")
	public String reqRefreshAccessTocken(HttpSession session, @RequestParam(required=false, defaultValue="/") String returnUrl) throws IOException {
		OAuth2AccessToken oauthToken = (OAuth2AccessToken)session.getAttribute("oauthToken"); 
		OAuth2AccessToken newAccessToken = loginService.reqRefreshAccessToken(oauthToken);

		session.setAttribute("oauthToken", newAccessToken);
		session.setAttribute("oauthTokenExpires", getExpireDate());
		return "redirect:"+ returnUrl;
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
