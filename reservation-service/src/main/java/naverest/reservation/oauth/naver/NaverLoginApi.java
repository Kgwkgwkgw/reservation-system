package naverest.reservation.oauth.naver;

import com.github.scribejava.core.builder.api.DefaultApi20;

//OAuth2.0 프로토콜을 지원하는 Scribe 라이브러리 사용 
public class NaverLoginApi extends DefaultApi20 {
	
	private static class InstanceHolder{
        private static final NaverLoginApi INSTANCE = new NaverLoginApi();
	}


    public static NaverLoginApi instance(){
        return InstanceHolder.INSTANCE;
    }

	
    @Override
    public String getAccessTokenEndpoint() {
        return "https://nid.naver.com/oauth2.0/token";
    }                   

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://nid.naver.com/oauth2.0/authorize";
    }   
}
