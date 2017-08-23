package naverest.reservation.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;

import naverest.reservation.domain.User;

public abstract class LoginService {
	
	public String getAuthorizationUrl(String oauthState, String returnUrl) {        
		try {
			returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
//			log.error("{}",e);
		}
	    OAuth20Service oauthService = getOauthService(oauthState, returnUrl);                                                   
	    
//	    log.info(oauthService.getAuthorizationUrl());
	    return oauthService.getAuthorizationUrl();
	}
	
	public OAuth2AccessToken reqAccessToken(String oauthState, String code, String state) throws IOException{
	    if(StringUtils.pathEquals(oauthState, state)){
		    	OAuth20Service oauthService = getOauthService();
	
	        OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
	        return accessToken;
	    }
	    return null;
	}
	public OAuth2AccessToken reqRefreshAccessToken(OAuth2AccessToken accessToken) {
		OAuth20Service oauthService = getOauthService();
		try {
			return oauthService.refreshAccessToken(accessToken.getRefreshToken());
		} catch (IOException e) {
//			log.info("{}",e);
			 throw new RuntimeException("로그인 리프레시 실패", e);
		}
	}
	
	public abstract OAuth20Service getOauthService(String oauthState, String returnUrl);
	public abstract OAuth20Service getOauthService();
	public abstract User getUserProfile(OAuth2AccessToken oauthToken) throws IOException;
	public abstract User setUser(Response response) throws IOException;
}
