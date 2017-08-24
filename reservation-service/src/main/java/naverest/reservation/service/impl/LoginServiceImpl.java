package naverest.reservation.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.scribejava.core.model.OAuth2AccessToken;

import naverest.reservation.domain.User;
import naverest.reservation.factory.impl.OAuthConnectorFactory;
import naverest.reservation.oauth.OAuthConnector;

@Service
public class LoginServiceImpl {
	private final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	private OAuthConnectorFactory oAuthConnectorFactory;
	
	@Autowired
	public LoginServiceImpl() {
		oAuthConnectorFactory = OAuthConnectorFactory.getInstance();
	}
	
	public User getUser(String sns,  OAuth2AccessToken oauthToken) throws IOException {
		OAuthConnector oAuthConnector = oAuthConnectorFactory.getOAuthConnector(sns);
		return oAuthConnector.reqProfile(oauthToken);
	}
	
	public String getAuthorizationUrl(String sns, String oauthState, String returnUrl) throws IOException {
		OAuthConnector oAuthConnector = oAuthConnectorFactory.getOAuthConnector(sns);
		try {
			returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("{}",e);
		}                                                   
		return oAuthConnector.buildAuthorizationUrl(oauthState, returnUrl);
	}
	
	public OAuth2AccessToken getAccessToken(String sns, String oauthState, String code, String state) throws IOException {
		OAuthConnector oAuthConnector = oAuthConnectorFactory.getOAuthConnector(sns);
		return oAuthConnector.reqAccessToken(oauthState, code, state);
	}
	
	public OAuth2AccessToken getRefreshedAccessToken(String sns, OAuth2AccessToken oauthToken) throws IOException {
		OAuthConnector oAuthConnector = oAuthConnectorFactory.getOAuthConnector(sns);
		return oAuthConnector.reqRefreshedAccessToken(oauthToken);
	}
}
