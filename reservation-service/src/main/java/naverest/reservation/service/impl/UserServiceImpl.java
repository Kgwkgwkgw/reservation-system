package naverest.reservation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.controller.user.UserCommentController;
import naverest.reservation.dao.UserDao;
import naverest.reservation.domain.User;
import naverest.reservation.dto.NaverLoginProfile;
import naverest.reservation.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	private UserDao userDao;
	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public User login(User user) {
		User selected = userDao.selectBySnsId(user.getSnsId());
		if(selected != null )
			return selected;
		// 새로 생성 
		return create(user);
	}
	
	private User create(User user) {
		user.setId(userDao.insert(user));
		log.error("{}", user);
		return user;
	}
}
