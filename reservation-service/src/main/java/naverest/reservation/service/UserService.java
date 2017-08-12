package naverest.reservation.service;

import naverest.reservation.domain.User;
import naverest.reservation.dto.NaverLoginProfile;

public interface UserService {
	public User login(NaverLoginProfile naverLoginProfile);
}
