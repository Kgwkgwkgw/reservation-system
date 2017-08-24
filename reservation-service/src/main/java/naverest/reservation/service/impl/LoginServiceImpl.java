package naverest.reservation.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.scribejava.core.model.OAuth2AccessToken;

import naverest.reservation.domain.User;
import naverest.reservation.factory.impl.OAuthConnectorFactory;
import naverest.reservation.oauth.OAuthConnector;
import naverest.reservation.service.LoginService;
import naverest.reservation.service.UserService;

@Service
public class LoginServiceImpl implements LoginService{
	private final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	private OAuthConnectorFactory oAuthConnectorFactory;
	private UserService userService;

	@Autowired
	public LoginServiceImpl(UserService userService) {
		oAuthConnectorFactory = OAuthConnectorFactory.getInstance();
		this.userService = userService;
	}

	public User login(String sns, OAuth2AccessToken oauthToken) throws IOException {
		OAuthConnector oAuthConnector = oAuthConnectorFactory.getOAuthConnector(sns);
		User user = oAuthConnector.reqProfile(oauthToken);
		return userService.findOrCreate(user);
	}

	public String getAuthorizationUrl(String sns, String oauthState) throws IOException {
		OAuthConnector oAuthConnector = oAuthConnectorFactory.getOAuthConnector(sns);
		return oAuthConnector.buildAuthorizationUrl(oauthState);
	}

	public OAuth2AccessToken getAccessToken(String sns, String oauthState, String code, String state)
			throws IOException {
		OAuthConnector oAuthConnector = oAuthConnectorFactory.getOAuthConnector(sns);
		return oAuthConnector.reqAccessToken(oauthState, code, state);
	}

	public OAuth2AccessToken getRefreshedAccessToken(String sns, OAuth2AccessToken oauthToken) throws IOException {
		OAuthConnector oAuthConnector = oAuthConnectorFactory.getOAuthConnector(sns);
		return oAuthConnector.reqRefreshedAccessToken(oauthToken);
	}
}
