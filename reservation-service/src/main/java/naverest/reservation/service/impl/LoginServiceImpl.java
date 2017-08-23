//package naverest.reservation.service.impl;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.scribejava.core.model.OAuth2AccessToken;
//import com.github.scribejava.core.model.OAuthRequest;
//import com.github.scribejava.core.model.Response;
//import com.github.scribejava.core.model.Verb;
//import com.github.scribejava.core.oauth.OAuth20Service;
//
//import naverest.reservation.dto.NaverLoginProfile;
//import naverest.reservation.factory.OAuth20ServiceFactory;
//import naverest.reservation.factory.impl.GoogleOAuth20ServiceFactory;
//
//@Service
//public class LoginServiceImpl {
//	private final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
//	@Value("${naverest.naverlogin.profileApiUrl}")
//	private String PROFILE_API_URL;
//	
//	private OAuth20ServiceFactory naverOAuth20ServiceFactory;
//	private GoogleOAuth20ServiceFactory googleOAuth20ServiceFactory;
//	public LoginServiceImpl() {
////		naverOAuth20ServiceFactory = NaverOAuth20ServiceFactory.getInstance();
//		googleOAuth20ServiceFactory = GoogleOAuth20ServiceFactory.getInstance();
//	}
//	
//	public String getAuthorizationUrl(String oauthState, String returnUrl) {        
//		try {
//			returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			log.error("{}",e);
//		}
//	    OAuth20Service oauthService = googleOAuth20ServiceFactory.getOauthService(oauthState, returnUrl);                                                   
//	    
//	    log.info(oauthService.getAuthorizationUrl());
//	    
//	    return oauthService.getAuthorizationUrl();
//	}
//	
//	public NaverLoginProfile getUserProfile(OAuth2AccessToken oauthToken) throws IOException{
//		OAuth20Service oauthService = googleOAuth20ServiceFactory.getOauthService();
//		
//	    OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
//	    oauthService.signRequest(oauthToken, request);
//	    Response response = request.send();
//	     
//  		ObjectMapper objectMapper = new ObjectMapper();
//  		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//	    JsonNode rootNode = objectMapper.readTree(response.getBody());
//	    
//	    JsonNode responseNode = rootNode.path("response");
//	    NaverLoginProfile naverLoginProfile = objectMapper.treeToValue(responseNode, NaverLoginProfile.class);
//	    return naverLoginProfile;
//	}
//	
//	public OAuth2AccessToken reqAccessToken(String oauthState, String code, String state) throws IOException{
//	    if(StringUtils.pathEquals(oauthState, state)){
//		    	OAuth20Service oauthService = googleOAuth20ServiceFactory.getOauthService();
//	
//	        OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
//	        return accessToken;
//	    }
//	    return null;
//	}
//	public OAuth2AccessToken reqRefreshAccessToken(OAuth2AccessToken accessToken) {
//		OAuth20Service oauthService = googleOAuth20ServiceFactory.getOauthService();
//		try {
//			return oauthService.refreshAccessToken(accessToken.getRefreshToken());
//		} catch (IOException e) {
//			log.info("{}",e);
//			 throw new RuntimeException("로그인 리프레시 실패", e);
//		}
//	}
//	public void getA(OAuth2AccessToken oauthToken ) {
//		OAuth20Service oauthService = googleOAuth20ServiceFactory.getOauthService();
//		OAuthRequest request = new OAuthRequest(Verb.GET,"https://www.googleapis.com/plus/v1/people/me", oauthService);
//		System.out.println(request.getUrl());
//		oauthService.signRequest(oauthToken, request);
//		request.addParameter("key", "AIzaSyDHNDOznM7eKKs4nnAfTu1OwisyeBmne84");
//		System.out.println(request.getCompleteUrl());
//		System.out.println(request.send());
//	}
//
//}
