package naverest.reservation.factory.impl;

import naverest.reservation.oauth.OAuthConnector;
import naverest.reservation.oauth.impl.GoogleOAuthConnector;
import naverest.reservation.oauth.impl.NaverOAuthConnector;

public class OAuthConnectorFactory {
private volatile static OAuthConnectorFactory oAuthConnectorFactory;
	
	private OAuthConnectorFactory() {
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
			return new GoogleOAuthConnector();
		}
		else if("naver".equals(sns)) {
			return new NaverOAuthConnector();
		}
		throw new RuntimeException("지원하지않는 sns");
		
	}
}
