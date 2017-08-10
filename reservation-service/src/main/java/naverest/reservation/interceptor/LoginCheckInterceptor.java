package naverest.reservation.interceptor;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.scribejava.core.model.OAuth2AccessToken;

import naverest.reservation.domain.User;
import naverest.reservation.oauth.naver.NaverLoginApiService;
import naverest.reservation.security.SecurityContext;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	private NaverLoginApiService naverApiBO;

	@Autowired
	public LoginCheckInterceptor(NaverLoginApiService naverApiBO) {
		this.naverApiBO = naverApiBO;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		StringBuilder requestURI = new StringBuilder(request.getRequestURI());
		String queryString = request.getQueryString();
//		requestURI = URLEncoder.encode(requestURI, "UTF-8");

		StringBuilder returnUrl = new StringBuilder("?returnUrl=");
		User user = (User) session.getAttribute("loginInfo");

		if (user == null) {
			if (queryString != null) {
				requestURI.append("?").append(queryString);
//				queryString = URLEncoder.encode(queryString, "UTF-8");
//				returnUrl.append("?").append(queryString);
			}
			
			response.sendRedirect("/login" + returnUrl.append(URLEncoder.encode(requestURI.toString(),"UTF-8")));
			return false;
		} else {
			SecurityContext.loginUser.set(user);
		}

		Date current = new Date();
		// access_token 만기 시, refresh함.
		if (current.compareTo((Date) session.getAttribute("oauthTokenExpires")) > 0) {
			session.setAttribute("oauthToken",
					naverApiBO.reqRefreshAccessTocken((OAuth2AccessToken) session.getAttribute("oauthToken")));
		}
		return true;
	}
}