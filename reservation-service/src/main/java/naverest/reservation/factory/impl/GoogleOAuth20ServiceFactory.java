package naverest.reservation.factory.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import naverest.reservation.factory.OAuth20ServiceFactory;
import naverest.reservation.oauth.impl.GoogleLoginApi;

public class GoogleOAuth20ServiceFactory implements OAuth20ServiceFactory {
	private String API_KEY;
	private String API_SECRET;
	private String REDIRECT_URI;
	
	private volatile static GoogleOAuth20ServiceFactory googleOAuth20ServiceFactory;
	
	private GoogleOAuth20ServiceFactory() {
		PropertiesConfiguration config = new PropertiesConfiguration();
		try {
			config.load("application.properties");
			API_KEY = config.getString("naverest.googlelogin.apiKey");
			API_SECRET = config.getString("naverest.googlelogin.apiSecret");
			REDIRECT_URI = config.getString("naverest.googlelogin.redirectUri");
			
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static GoogleOAuth20ServiceFactory getInstance() {
		if(googleOAuth20ServiceFactory == null) {
			synchronized (GoogleOAuth20ServiceFactory.class) {
				if(googleOAuth20ServiceFactory == null) {
					googleOAuth20ServiceFactory = new GoogleOAuth20ServiceFactory();
				}
			}
		}
		return googleOAuth20ServiceFactory;
	}
	
	@Override
	public OAuth20Service makeOauthService() {
		return new ServiceBuilder()
				.apiKey(API_KEY)
				.apiSecret(API_SECRET)
				.callback(REDIRECT_URI)
				.scope("openid email profile")
				.build(GoogleLoginApi.instance());
	}

	@Override
	public OAuth20Service makeOauthService(String state) {
		return new ServiceBuilder()                                                   
			        .apiKey(API_KEY)
			        .apiSecret(API_SECRET)
			        .callback(REDIRECT_URI)
			        .scope("openid email profile")
			        .state(state) 
			        .build(GoogleLoginApi.instance());
	}

}
