package naverest.reservation.factory.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import naverest.reservation.factory.OAuth20ServiceFactory;
import naverest.reservation.oauth.impl.NaverLoginApi;

public class NaverOAuth20ServiceFactory implements OAuth20ServiceFactory {
	private String API_KEY;
	private String API_SECRET;
	private String REDIRECT_URI;
	
	private volatile static NaverOAuth20ServiceFactory naverOAuth20ServiceFactory;
	
	private NaverOAuth20ServiceFactory() {
		PropertiesConfiguration config = new PropertiesConfiguration();
		try {
			
			config.load("application.properties");
			API_KEY = config.getString("naverest.naverlogin.apiKey");
			API_SECRET = config.getString("naverest.naverlogin.apiSecret");
			REDIRECT_URI = config.getString("naverest.naverlogin.redirectUri");
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static NaverOAuth20ServiceFactory getInstance() {
		if(naverOAuth20ServiceFactory == null) {
			synchronized (NaverOAuth20ServiceFactory.class) {
				if(naverOAuth20ServiceFactory == null) {
					naverOAuth20ServiceFactory = new NaverOAuth20ServiceFactory();
				}
			}
		}
		return naverOAuth20ServiceFactory;
	}
	
	@Override
	public OAuth20Service makeOauthService() {
		return new ServiceBuilder()                                                   
		            .apiKey(API_KEY)
		            .apiSecret(API_SECRET)
		            .callback(REDIRECT_URI)
		            .build(NaverLoginApi.instance());
	}

	@Override
	public OAuth20Service makeOauthService(String state) {
		return new ServiceBuilder()                                                   
			        .apiKey(API_KEY)
			        .apiSecret(API_SECRET)
			        .callback(REDIRECT_URI)
			        .state(state) 
			        .build(NaverLoginApi.instance());
	}

}
