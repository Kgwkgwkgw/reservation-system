package naverest.reservation.service;

import java.io.IOException;

import com.github.scribejava.core.model.OAuth2AccessToken;

import naverest.reservation.domain.User;

public interface LoginService {
	User login(String sns, OAuth2AccessToken oauthToken) throws IOException;
	String getAuthorizationUrl(String sns, String oauthState) throws IOException;
	OAuth2AccessToken getAccessToken(String sns, String oauthState, String code, String state) throws IOException;
	OAuth2AccessToken getRefreshedAccessToken(String sns, OAuth2AccessToken oauthToken) throws IOException;
}
