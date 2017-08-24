package naverest.reservation.oauth;

import java.io.IOException;

import org.springframework.util.StringUtils;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import naverest.reservation.domain.User;

public abstract class OAuthConnector {
	public final User reqProfile(OAuth2AccessToken oauthToken) throws IOException {
		OAuth20Service oauthService = getOauthService();
		
	    OAuthRequest request = new OAuthRequest(Verb.GET, getProfileApiUrl(), oauthService);
	    oauthService.signRequest(oauthToken, request);
	    
	    return buildUser(request.send());
	}
	public final String buildAuthorizationUrl(String oauthState, String returnUrl) {
		return getOauthService(oauthState, returnUrl).getAuthorizationUrl();
	}
	public final OAuth2AccessToken reqAccessToken(String oauthState, String code, String state) throws IOException{
	    if(StringUtils.pathEquals(oauthState, state)){
	    	
	        return  getOauthService().getAccessToken(code);
	    }
	    return null;
	}
	public final OAuth2AccessToken reqRefreshedAccessToken(OAuth2AccessToken oauthToken) throws IOException {
		return getOauthService().refreshAccessToken(oauthToken.getRefreshToken());
	}
	protected abstract OAuth20Service getOauthService(String oauthState, String returnUrl);
	protected abstract OAuth20Service getOauthService();
	protected abstract String getProfileApiUrl();
	protected abstract User buildUser(Response response) throws IOException;
}
