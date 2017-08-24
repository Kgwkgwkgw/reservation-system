package naverest.reservation.factory.impl;

import naverest.reservation.oauth.OAuthConnector;
import naverest.reservation.oauth.impl.GoogleOAuthConnector;
import naverest.reservation.oauth.impl.NaverOAuthConnector;

public class OAuthConnectorFactory {
private volatile static OAuthConnectorFactory oAuthConnectorFactory;
	private GoogleOAuthConnector googleOauthConnector;
	private NaverOAuthConnector naverOAuthConnector;
	private OAuthConnectorFactory() {
		this.googleOauthConnector = new GoogleOAuthConnector();
		this.naverOAuthConnector = new NaverOAuthConnector();
	}
	public static OAuthConnectorFactory getInstance() {
		if(oAuthConnectorFactory == null) {
			synchronized (OAuthConnectorFactory.class) {
				if(oAuthConnectorFactory == null) {
					oAuthConnectorFactory = new OAuthConnectorFactory();
				}
			}
		}
		return oAuthConnectorFactory;
	}
	public OAuthConnector getOAuthConnector(String sns) {
		if("google".equals(sns)) {
			return googleOauthConnector;
		}
		else if("naver".equals(sns)) {
			return naverOAuthConnector;
		}
		throw new RuntimeException("지원하지않는 sns");
		
	}
}
