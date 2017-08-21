package naverest.reservation.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import naverest.reservation.dto.NaverLoginProfile;
import naverest.reservation.oauth.naver.NaverLoginApi;

@Service
public class NaverLoginServiceImpl {
	@Value ("${naverest.naverlogin.client.id}")
	private String CLIENT_ID;
	@Value("${naverest.naverlogin.client.secret}")
	private String CLIENT_SECRET;
	@Value("${naverest.naverlogin.redirectUri}")
	private String REDIRECT_URI;
	@Value("${naverest.naverlogin.profileApiUrl}")
	private String PROFILE_API_URL;
	private final Logger log = LoggerFactory.getLogger(NaverLoginServiceImpl.class);
	
	public String getAuthorizationUrl(String oauthState, String returnUrl) {        
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
	
	public NaverLoginProfile getUserProfile(OAuth2AccessToken oauthToken) throws IOException{
		OAuth20Service oauthService = new ServiceBuilder()                                                   
	            .apiKey(CLIENT_ID)
	            .apiSecret(CLIENT_SECRET)
	            .callback(REDIRECT_URI)
	            .build(NaverLoginApi.instance());
		
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
	
	public OAuth2AccessToken reqAccessToken(String oauthState, String code, String state) throws IOException{
	    if(StringUtils.pathEquals(oauthState, state)){
		    	OAuth20Service oauthService = new ServiceBuilder()
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
	public OAuth2AccessToken reqRefreshAccessToken(OAuth2AccessToken accessToken) {
		OAuth20Service oauthService = new ServiceBuilder()                                                   
	            .apiKey(CLIENT_ID)
	            .apiSecret(CLIENT_SECRET)
	            .callback(REDIRECT_URI)
	            .build(NaverLoginApi.instance());
		try {
			return oauthService.refreshAccessToken(accessToken.getRefreshToken());
		} catch (IOException e) {
			log.info("{}",e);
			 throw new RuntimeException("로그인 리프레시 실패", e);
		}
	}

}
