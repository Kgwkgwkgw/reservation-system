package naverest.reservation.interceptor;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.scribejava.core.model.OAuth2AccessToken;

import naverest.reservation.controller.user.login.LoginController;
import naverest.reservation.domain.User;
import naverest.reservation.security.SecurityContext;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	private LoginController loginController;

	@Autowired
	public LoginCheckInterceptor(LoginController loginController) {
		this.loginController = loginController;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		StringBuilder requestURI = new StringBuilder(request.getRequestURI());
		String queryString = request.getQueryString();

		StringBuilder returnUrl = new StringBuilder("?returnUrl=");
		User user = (User) session.getAttribute("loginInfo");

		if (user == null) {
			if (queryString != null) {
				requestURI.append("?").append(queryString);
			}
			
			response.sendRedirect("/login" + returnUrl.append(URLEncoder.encode(requestURI.toString(),"UTF-8")));
			return false;
		} else {
			SecurityContext.loginUser.set(user);
		}

		Date current = new Date();
		if (current.compareTo((Date) session.getAttribute("oauthTokenExpires")) > 0) {
			session.setAttribute("oauthToken",
					loginController.reqRefreshAccessTocken((OAuth2AccessToken) session.getAttribute("oauthToken")));
		}
		return true;
	}
}