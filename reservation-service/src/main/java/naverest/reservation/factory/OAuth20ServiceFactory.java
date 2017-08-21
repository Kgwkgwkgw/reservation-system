package naverest.reservation.factory;

import com.github.scribejava.core.oauth.OAuth20Service;

public interface OAuth20ServiceFactory {
	OAuth20Service getOauthService();
	OAuth20Service getOauthService(String state, String returnUrl);
}
