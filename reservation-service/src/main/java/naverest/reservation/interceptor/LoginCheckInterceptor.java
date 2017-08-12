package naverest.reservation.interceptor;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import naverest.reservation.domain.User;
import naverest.reservation.security.SecurityContext;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	public LoginCheckInterceptor() {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		StringBuilder requestURI = new StringBuilder(request.getRequestURI());
		String queryString = request.getQueryString();
		
		if (queryString != null) {
			requestURI.append("?").append(queryString);
		}
		
		StringBuilder returnUrl = new StringBuilder("?returnUrl=")
									 .append(URLEncoder.encode(requestURI.toString(),"UTF-8"));
		
		User user = (User) session.getAttribute("loginInfo");
		if (user == null) {
			response.sendRedirect("/login" + returnUrl.toString());
			return false;
		} 
		
		Date current = new Date();
		if (current.compareTo((Date) session.getAttribute("oauthTokenExpires")) > 0) {
			response.sendRedirect("/login/refresh" + returnUrl.toString());
		}
		
		SecurityContext.loginUser.set(user);
		return true;
	}
}