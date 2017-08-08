package naverest.reservation.security;

import naverest.reservation.domain.User;

public class SecurityContext {
	public static ThreadLocal<User> loginUser = new ThreadLocal<User>();
}
