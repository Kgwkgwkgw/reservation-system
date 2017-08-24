package naverest.reservation.oauth.impl;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class GoogleLoginApi extends DefaultApi20 {
	private static class InstanceHolder{
        private static final GoogleLoginApi INSTANCE = new GoogleLoginApi();
	}

    public static GoogleLoginApi instance(){
        return InstanceHolder.INSTANCE;
    }

	
    @Override
    public String getAccessTokenEndpoint() {
        return "https://accounts.google.com/o/oauth2/token";
    }                   

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://accounts.google.com/o/oauth2/auth";
    }
}
