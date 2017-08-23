package naverest.reservation.service.impl;

import java.io.IOException;
import java.util.Date;

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
import naverest.reservation.dto.NaverLoginProfile;
import naverest.reservation.oauth.NaverLoginApi;
import naverest.reservation.service.LoginService;

@Service("naver")
public class NaverLoginServiceImpl extends LoginService {
	private final Logger log = LoggerFactory.getLogger(GoogleLoginServiceImpl.class);
	@Value("${naverest.naverlogin.profileApiUrl}")
	private String PROFILE_API_URL;
	@Value("${naverest.naverlogin.apiKey}")
	private String API_KEY;
	@Value("${naverest.naverlogin.apiSecret}")
	private String API_SECRET;
	@Value("${naverest.naverlogin.redirectUri}")
	private String REDIRECT_URI;
	
	@Override
	public OAuth20Service getOauthService(String oauthState, String returnUrl) {
		return new ServiceBuilder()                                                   
		        .apiKey(API_KEY)
		        .apiSecret(API_SECRET)
		        .callback(REDIRECT_URI + "?returnUrl="+returnUrl)
		        .state(oauthState) 
		        .build(NaverLoginApi.instance());
	}
	
	@Override
	public OAuth20Service getOauthService() {
		return new ServiceBuilder()                                                   
	            .apiKey(API_KEY)
	            .apiSecret(API_SECRET)
	            .callback(REDIRECT_URI)
	            .build(NaverLoginApi.instance());
	}
	@Override
	public User getUserProfile(OAuth2AccessToken oauthToken) throws IOException{
	OAuth20Service oauthService = getOauthService();
	
    OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
    oauthService.signRequest(oauthToken, request);
    Response response = request.send();
    return setUser(response);
}
	@Override
	public User setUser(Response response) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    JsonNode rootNode = objectMapper.readTree(response.getBody());
	    JsonNode responseNode = rootNode.path("response");
	    NaverLoginProfile naverLoginProfile = objectMapper.treeToValue(responseNode, NaverLoginProfile.class);
	    
	    User user = new User();
	    user.setUserName(naverLoginProfile.getName());
		user.setEmail(naverLoginProfile.getEmail());
		user.setNickname(naverLoginProfile.getNickname());
		user.setSnsId(naverLoginProfile.getId().toString());
		user.setSnsType("naver");
		user.setSnsProfile(naverLoginProfile.getProfileImage());
		user.setAdminFlag(0);
		user.setCreateDate(new Date());
		return user;
	}

}
