package naverest.reservation.factory.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import naverest.reservation.factory.OAuth20ServiceFactory;
import naverest.reservation.oauth.naver.NaverLoginApi;

public class NaverOAuth20ServiceFactory implements OAuth20ServiceFactory {
	private String CLIENT_ID;
	private String CLIENT_SECRET;
	private String REDIRECT_URI;	
	
	private volatile static NaverOAuth20ServiceFactory naverOAuth20ServiceFactory;
	
	private NaverOAuth20ServiceFactory() {
		PropertiesConfiguration config = new PropertiesConfiguration();
		try {
			
			config.load("application.properties");
			CLIENT_ID = config.getString("naverest.naverlogin.client.id");
			CLIENT_SECRET = config.getString("naverest.naverlogin.client.secret");
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
	public OAuth20Service getOauthService() {
		return new ServiceBuilder()                                                   
		            .apiKey(CLIENT_ID)
		            .apiSecret(CLIENT_SECRET)
		            .callback(REDIRECT_URI)
		            .build(NaverLoginApi.instance());
	}

	@Override
	public OAuth20Service getOauthService(String state, String returnUrl) {
		return new ServiceBuilder()                                                   
			        .apiKey(CLIENT_ID)
			        .apiSecret(CLIENT_SECRET)
			        .callback(REDIRECT_URI + "?returnUrl="+returnUrl)
			        .state(state) 
			        .build(NaverLoginApi.instance());
	}

}
