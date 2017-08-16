package naverest.reservation.service;

import naverest.reservation.domain.FileDomain;
import naverest.reservation.domain.User;
import naverest.reservation.dto.NaverLoginProfile;

public interface UserService {
	User login(NaverLoginProfile naverLoginProfile);
	
}
