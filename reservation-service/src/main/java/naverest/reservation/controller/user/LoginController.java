package naverest.reservation.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import naverest.reservation.domain.User;
import naverest.reservation.dto.NaverLoginProfile;
import naverest.reservation.oauth.naver.NaverLoginApi;
import naverest.reservation.service.UserService;
import naverest.reservation.service.impl.NaverLoginServiceImpl;

@Controller
@RequestMapping("/login")
public class LoginController {
	private UserService userService;
	private NaverLoginServiceImpl naverLoginService;
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	private final static String SESSION_STATE = "oauthState";

	@Autowired
	public LoginController(UserService userService, NaverLoginServiceImpl naverLoginService) {
		this.userService = userService;
		this.naverLoginService = naverLoginService;
	}
	
	@GetMapping
	public String login(HttpSession session, @RequestParam String returnUrl) {
		String oauthState = generateRandomString();
		session.setAttribute(SESSION_STATE, oauthState);
		String naverAuthUrl = naverLoginService.getAuthorizationUrl(oauthState, returnUrl);

		return "redirect:" + naverAuthUrl;
	}

	@GetMapping("/callback")
	public String callback(@RequestParam String code, @RequestParam String state, @RequestParam String returnUrl,
			@RequestParam(required = false) String error, HttpSession session) throws IOException {
		if (error == null) {
			OAuth2AccessToken oauthToken;
			
			oauthToken = naverLoginService.reqAccessToken((String) session.getAttribute(SESSION_STATE), code, state);
			session.setAttribute("oauthToken", oauthToken);
			session.setAttribute("oauthTokenExpires", getExpireDate());

			NaverLoginProfile naverLoginProfile = naverLoginService.getUserProfile(oauthToken);

			User user = userService.login(naverLoginProfile);

			session.setAttribute("loginInfo", user);
			return "redirect:" + returnUrl;
		} else {
			log.debug(error);
			return "error";
		}
	}
	
	@GetMapping("/refresh")
	public String reqRefreshAccessTocken(HttpSession session, @RequestParam String returnUrl) throws IOException {
		OAuth2AccessToken oauthToken = (OAuth2AccessToken)session.getAttribute("oauthToken"); 
		OAuth2AccessToken newAccessToken = naverLoginService.reqRefreshAccessToken(oauthToken);

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
