//<<<<<<< Updated upstream
////package naverest.reservation.factory.impl;
////
////import org.apache.commons.configuration.ConfigurationException;
////import org.apache.commons.configuration.PropertiesConfiguration;
////
////import com.github.scribejava.core.builder.ServiceBuilder;
////import com.github.scribejava.core.oauth.OAuth20Service;
////
////import naverest.reservation.factory.OAuth20ServiceFactory;
////import naverest.reservation.oauth.GoogleLoginApi;
////
////public class GoogleOAuth20ServiceFactory implements OAuth20ServiceFactory {
////	private String CLIENT_ID;
////	private String CLIENT_SECRET;
////	private String REDIRECT_URI;	
////	
////	private volatile static GoogleOAuth20ServiceFactory googleOAuth20ServiceFactory;
////	
////	private GoogleOAuth20ServiceFactory() {
////		PropertiesConfiguration config = new PropertiesConfiguration();
////		try {
////			
////			config.load("application.properties");
////			CLIENT_ID = config.getString("naverest.googlelogin.apiKey");
////			CLIENT_SECRET = config.getString("naverest.googlelogin.apiSecret");
////			REDIRECT_URI = config.getString("naverest.login.redirectUri");
////			
////		} catch (ConfigurationException e) {
////			throw new RuntimeException(e);
////		}
////	}
////	
////	public static GoogleOAuth20ServiceFactory getInstance() {
////		if(googleOAuth20ServiceFactory == null) {
////			synchronized (GoogleOAuth20ServiceFactory.class) {
////				if(googleOAuth20ServiceFactory == null) {
////					googleOAuth20ServiceFactory = new GoogleOAuth20ServiceFactory();
////				}
////			}
////		}
////		return googleOAuth20ServiceFactory;
////	}
////	
////	@Override
////	public OAuth20Service getOauthService() {
////		return new ServiceBuilder()                                                   
////		            .apiKey(CLIENT_ID)
////		            .apiSecret(CLIENT_SECRET)
////		            .callback(REDIRECT_URI)
////<<<<<<< Updated upstream
////		            .scope("openid email profile")
////=======
////		            .scope("profile")
////>>>>>>> Stashed changes
////		            .build(GoogleLoginApi.instance());
////	}
////
////	@Override
////	public OAuth20Service getOauthService(String state, String returnUrl) {
////		return new ServiceBuilder()                                                   
////			        .apiKey(CLIENT_ID)
////			        .apiSecret(CLIENT_SECRET)
////			        .callback(REDIRECT_URI)
////			        .scope("openid email profile")
////			        .state(state) 
////			        .build(GoogleLoginApi.instance());
////	}
////
////}
//=======
//package naverest.reservation.factory.impl;
//
//import org.apache.commons.configuration.ConfigurationException;
//import org.apache.commons.configuration.PropertiesConfiguration;
//
//import com.github.scribejava.core.builder.ServiceBuilder;
//import com.github.scribejava.core.oauth.OAuth20Service;
//
//import naverest.reservation.factory.OAuth20ServiceFactory;
//import naverest.reservation.oauth.GoogleLoginApi;
//
//public class GoogleOAuth20ServiceFactory implements OAuth20ServiceFactory {
//	private String CLIENT_ID;
//	private String CLIENT_SECRET;
//	private String REDIRECT_URI;	
//	
//	private volatile static GoogleOAuth20ServiceFactory googleOAuth20ServiceFactory;
//	
//	private GoogleOAuth20ServiceFactory() {
//		PropertiesConfiguration config = new PropertiesConfiguration();
//		try {
//			
//			config.load("application.properties");
//			CLIENT_ID = config.getString("naverest.googlelogin.apiKey");
//			CLIENT_SECRET = config.getString("naverest.googlelogin.apiSecret");
//			REDIRECT_URI = config.getString("naverest.login.redirectUri");
//			
//		} catch (ConfigurationException e) {
//			throw new RuntimeException(e);
//		}
//	}
//	
//	public static GoogleOAuth20ServiceFactory getInstance() {
//		if(googleOAuth20ServiceFactory == null) {
//			synchronized (GoogleOAuth20ServiceFactory.class) {
//				if(googleOAuth20ServiceFactory == null) {
//					googleOAuth20ServiceFactory = new GoogleOAuth20ServiceFactory();
//				}
//			}
//		}
//		return googleOAuth20ServiceFactory;
//	}
//	
//	@Override
//	public OAuth20Service getOauthService() {
//		return new ServiceBuilder()                                                   
//		            .apiKey(CLIENT_ID)
//		            .apiSecret(CLIENT_SECRET)
//		            .callback(REDIRECT_URI)
//		            .scope("profile")
//		            .build(GoogleLoginApi.instance());
//	}
//
//	@Override
//	public OAuth20Service getOauthService(String state, String returnUrl) {
//		return new ServiceBuilder()                                                   
//			        .apiKey(CLIENT_ID)
//			        .apiSecret(CLIENT_SECRET)
//			        .callback(REDIRECT_URI)
//			        .scope("openid email profile")
//			        .state(state) 
//			        .build(GoogleLoginApi.instance());
//	}
//
//}
//>>>>>>> Stashed changes
