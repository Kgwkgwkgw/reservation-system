package naverest.reservation.oauth.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;

import naverest.reservation.domain.User;
import naverest.reservation.dto.NaverLoginProfile;
import naverest.reservation.oauth.OAuthConnector;

public class NaverOAuthConnector extends OAuthConnector {
	private String API_KEY;
	private String API_SECRET;
	private String REDIRECT_URI;
	private String PROFILE_API_URL;
	
	public NaverOAuthConnector() {
		PropertiesConfiguration config = new PropertiesConfiguration();
		try {
			config.load("application.properties");
			API_KEY = config.getString("naverest.naverlogin.apiKey");
			API_SECRET = config.getString("naverest.naverlogin.apiSecret");
			REDIRECT_URI = config.getString("naverest.naverlogin.redirectUri");
			PROFILE_API_URL = config.getString("naverest.naverlogin.profileApiUrl");
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected OAuth20Service getOauthService(String oauthState, String returnUrl) {
		return new ServiceBuilder()                                                   
			        .apiKey(API_KEY)
			        .apiSecret(API_SECRET)
			        .callback(REDIRECT_URI + "?returnUrl="+returnUrl)
			        .state(oauthState) 
			        .build(NaverLoginApi.instance());
	}

	@Override
	protected OAuth20Service getOauthService() {
		return new ServiceBuilder()                                                   
		            .apiKey(API_KEY)
		            .apiSecret(API_SECRET)
		            .callback(REDIRECT_URI)
		            .build(NaverLoginApi.instance());
	}

	@Override
	protected String getProfileApiUrl() {
		return PROFILE_API_URL;
	}

	@Override
	protected User buildUser(Response response) throws IOException {
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
