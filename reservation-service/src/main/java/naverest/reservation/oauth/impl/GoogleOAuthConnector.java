package naverest.reservation.oauth.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;

import naverest.reservation.domain.User;
import naverest.reservation.dto.GoogleLoginProfile;
import naverest.reservation.oauth.OAuthConnector;

public class GoogleOAuthConnector extends OAuthConnector {
	private String API_KEY;
	private String API_SECRET;
	private String REDIRECT_URI;
	private String PROFILE_API_URL;
	
	public GoogleOAuthConnector() {
		PropertiesConfiguration config = new PropertiesConfiguration();
		try {
			
			config.load("application.properties");
			API_KEY = config.getString("naverest.googlelogin.apiKey");
			API_SECRET = config.getString("naverest.googlelogin.apiSecret");
			REDIRECT_URI = config.getString("naverest.googlelogin.redirectUri");
			PROFILE_API_URL = config.getString("naverest.googlelogin.profileApiUrl");
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	protected OAuth20Service getOauthService(String oauthState, String returnUrl) {
		return new ServiceBuilder()
					.apiKey(API_KEY)
					.apiSecret(API_SECRET)
					.callback(REDIRECT_URI)
					.scope("openid email profile")
					.state(oauthState)
					.build(GoogleLoginApi.instance());
	}

	@Override
	protected OAuth20Service getOauthService() {
		return new ServiceBuilder()
					.apiKey(API_KEY)
					.apiSecret(API_SECRET)
					.callback(REDIRECT_URI)
					.scope("openid email profile")
					.build(GoogleLoginApi.instance());
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
		User user = new User();

		GoogleLoginProfile googleLoginProfile = objectMapper.treeToValue(rootNode, GoogleLoginProfile.class);

		String familyName = "";
		String givenName = "";
		String snsProfile = "";
		String email = "";
		String snsId = "";
		String displayName = "";

		snsId = googleLoginProfile.getId();
		displayName = googleLoginProfile.getDisplayName();

		if (googleLoginProfile.getName().containsKey("familyName")) {
			familyName = googleLoginProfile.getName().get("familyName");
		}

		if (googleLoginProfile.getName().containsKey("givenName")) {
			givenName = googleLoginProfile.getName().get("givenName");
		}

		if (googleLoginProfile.getImage().containsKey("url")) {
			snsProfile = googleLoginProfile.getImage().get("url");
		}

		List<Map<String, String>> emails = googleLoginProfile.getEmails();
		if (emails != null) {
			for (Map<String, String> emailMap : emails) {
				if ("account".equals(emailMap.get("type"))) {
					email = emailMap.get("value");
				}
			}
		}

		user.setSnsId(snsId);
		user.setNickname(displayName);
		user.setUserName(familyName + givenName);
		user.setAdminFlag(0);
		user.setSnsProfile(snsProfile);
		user.setSnsType("google");
		user.setEmail(email);

		return user;
	}

}
