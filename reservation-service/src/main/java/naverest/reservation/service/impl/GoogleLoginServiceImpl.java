package naverest.reservation.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
import naverest.reservation.dto.GoogleLoginProfile;
import naverest.reservation.oauth.GoogleLoginApi;
import naverest.reservation.service.LoginService;

@Service("google")
public class GoogleLoginServiceImpl extends LoginService {
	private final Logger log = LoggerFactory.getLogger(GoogleLoginServiceImpl.class);
	@Value("${naverest.naverlogin.profileApiUrl}")
	private String PROFILE_API_URL;
	@Value("${naverest.googlelogin.apiKey}")
	private String API_KEY;
	@Value("${naverest.googlelogin.apiSecret}")
	private String API_SECRET;
	@Value("${naverest.googlelogin.redirectUri}")
	private String REDIRECT_URI;
	
	public GoogleLoginServiceImpl() {
	}
	
	public User getUserProfile(OAuth2AccessToken oauthToken ) throws IOException {
		OAuth20Service oauthService = getOauthService();
		OAuthRequest request = new OAuthRequest(Verb.GET,"https://www.googleapis.com/plus/v1/people/me", oauthService);
		
		oauthService.signRequest(oauthToken, request);
		Response response = request.send();

		return setUser(response);
	}
	public OAuth20Service getOauthService(String oauthState, String returnUrl) {
		return new ServiceBuilder()                                                   
	            .apiKey(API_KEY)
	            .apiSecret(API_SECRET)
	            .callback(REDIRECT_URI)
	            .scope("openid email profile")
	            .state(oauthState)
	            .build(GoogleLoginApi.instance());
	}
	public OAuth20Service getOauthService() {
		return new ServiceBuilder()                                                   
		            .apiKey(API_KEY)
		            .apiSecret(API_SECRET)
		            .callback(REDIRECT_URI)
		            .scope("openid email profile")
		            .build(GoogleLoginApi.instance());
	}
	public User setUser(Response response) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JsonNode rootNode = objectMapper.readTree(response.getBody());
		
		User user = new User();
		GoogleLoginProfile googleLoginProfile = objectMapper.treeToValue(rootNode, GoogleLoginProfile.class);
		user.setSnsId(googleLoginProfile.getId());
		user.setNickname(googleLoginProfile.getDisplayName());
		String familyName = "";
		String givenName = "";
		if(googleLoginProfile.getName().containsKey("familyName")) {
			familyName = googleLoginProfile.getName().get("familyName");
		}
		if(googleLoginProfile.getName().containsKey("givenName")) {
			givenName = googleLoginProfile.getName().get("givenName");
		}
		user.setUserName(familyName+givenName);
		
		List<Map<String, String>> emails = googleLoginProfile.getEmails();
		if(emails != null) {
			for(Map<String, String> email : emails) {
				if("account".equals(email.get("type"))) {
					user.setEmail(email.get("value"));
				}
			}
		}
		user.setAdminFlag(0);
		user.setSnsType("google");
		return user;
	}

}
