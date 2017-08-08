package naverest.reservation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.controller.review.ReviewController;
import naverest.reservation.dao.UserDao;
import naverest.reservation.domain.User;
import naverest.reservation.dto.NaverLoginProfile;

@Service
@Transactional
public class UserService {
	private UserDao userDao;
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User login(NaverLoginProfile naverLoginProfile) {
		User selected = userDao.selectBySnsId(naverLoginProfile.getId());
		if(selected != null )
			return selected;
		// 새로 생성 
		return create(naverLoginProfile);
	}
	
	private User create(NaverLoginProfile naverLoginProfile) {
		User user = new User(naverLoginProfile);
		user.setId(userDao.insert(user));
		log.error("{}", user);
		return user;
	}
}
