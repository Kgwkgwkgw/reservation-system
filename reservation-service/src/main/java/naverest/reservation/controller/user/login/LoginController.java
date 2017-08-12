package naverest.reservation.controller.user.login;

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

@Controller
@RequestMapping("/login")
public class LoginController {
	private UserService userService;
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	private final static String SESSION_STATE = "oauthState";
	@Value ("${naverest.naverlogin.client.id}")
	private String CLIENT_ID;
	@Value("${naverest.naverlogin.client.secret}")
	private String CLIENT_SECRET;
	@Value("${naverest.naverlogin.redirectUri}")
	private String REDIRECT_URI;
	@Value("${naverest.naverlogin.profileApiUrl}")
	private String PROFILE_API_URL;
	
	private OAuth20Service oauthService;

	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String login(HttpSession session, @RequestParam String returnUrl) {
		String oauthState = generateRandomString();
		session.setAttribute(SESSION_STATE, oauthState);
		String naverAuthUrl = getAuthorizationUrl(oauthState, returnUrl);

		return "redirect:" + naverAuthUrl;
	}

	@GetMapping("/callback")
	public String callback(@RequestParam String code, @RequestParam String state, @RequestParam String returnUrl,
			@RequestParam(required = false) String error, HttpSession session) throws IOException {
		if (error == null) {
			OAuth2AccessToken oauthToken;
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			oauthToken = reqAccessToken((String) session.getAttribute(SESSION_STATE), code, state);
			session.setAttribute("oauthToken", oauthToken);

			cal.add(Calendar.HOUR_OF_DAY, 1);
			session.setAttribute("oauthTokenExpires", cal.getTime());

			NaverLoginProfile naverLoginProfile = getUserProfile(oauthToken);

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
		OAuth2AccessToken newAccessToken = oauthService.refreshAccessToken(oauthToken.getRefreshToken());
		newAccessToken.getAccessToken();
		
		session.setAttribute("oauthToken", newAccessToken);
		return "redirect:"+ returnUrl;
	}

	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}
	  
	private String getAuthorizationUrl(String oauthState, String returnUrl) {         
		try {
			returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("{}",e);
		}
	    OAuth20Service oauthService = new ServiceBuilder()                                                   
	            .apiKey(CLIENT_ID)
	            .apiSecret(CLIENT_SECRET)
	            .callback(REDIRECT_URI + "?returnUrl="+returnUrl)
	            .state(oauthState) 
	            .build(NaverLoginApi.instance());
	    log.info(oauthService.getAuthorizationUrl());
	    return oauthService.getAuthorizationUrl();
	}
	 
	private OAuth2AccessToken reqAccessToken(String oauthState, String code, String state) throws IOException{
	    if(StringUtils.pathEquals(oauthState, state)){
	        oauthService = new ServiceBuilder()
	                .apiKey(CLIENT_ID)
	                .apiSecret(CLIENT_SECRET)
	                .callback(REDIRECT_URI)
	                .state(state)
	                .build(NaverLoginApi.instance());
	
	        OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
	        return accessToken;
	    }
	    return null;
	}
	
	private NaverLoginProfile getUserProfile(OAuth2AccessToken oauthToken) throws IOException{
	    OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
	    oauthService.signRequest(oauthToken, request);
	    Response response = request.send();
	     
  		ObjectMapper objectMapper = new ObjectMapper();
  		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    JsonNode rootNode = objectMapper.readTree(response.getBody());
	    
	    JsonNode responseNode = rootNode.path("response");
	    NaverLoginProfile naverLoginProfile = objectMapper.treeToValue(responseNode, NaverLoginProfile.class);
	    return naverLoginProfile;
	}

}
