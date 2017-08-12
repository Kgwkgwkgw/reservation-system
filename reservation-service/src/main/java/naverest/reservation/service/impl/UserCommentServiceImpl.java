package naverest.reservation.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.dao.FileDao;
import naverest.reservation.dao.UserCommentDao;
import naverest.reservation.dao.UserCommentImageDao;
import naverest.reservation.dto.CommentStats;
import naverest.reservation.dto.FileCommentImage;
import naverest.reservation.dto.UserComment;
import naverest.reservation.dto.UserCommentWrapper;
import naverest.reservation.service.UserCommentService;

@Service
@Transactional(readOnly = true)
public class UserCommentServiceImpl implements UserCommentService{
	private UserCommentDao userCommentDao;
	private FileDao fileDao;
	
	private final Logger log = LoggerFactory.getLogger(UserCommentServiceImpl.class);

	@Autowired
	public UserCommentServiceImpl(UserCommentDao userCommentDao, FileDao fileDao, UserCommentImageDao userCommentImageDao) {
		this.userCommentDao = userCommentDao;
		this.fileDao = fileDao;
	}

	@Override
	public UserCommentWrapper findCommentWrapperByProductId(Integer productId, Integer offset, Integer size) {
		UserCommentWrapper userCommentWrapper = new UserCommentWrapper();

		List<UserComment> userCommentList = findCommentListWithImage(productId, offset, size);
		CommentStats commentStats = findCommentStatsByProductId(productId);

		userCommentWrapper.setUserCommentList(userCommentList);
		userCommentWrapper.setCommentStats(commentStats);

		return userCommentWrapper;
	}

	private List<UserComment> findCommentListWithImage(Integer productId, Integer offset, Integer size) {
		List<UserComment> userCommentList = userCommentDao.selectUserCommentByProductId(productId, offset, size);
		log.info("{}",userCommentList);
		List<FileCommentImage> commentFileList = null;
		List<Integer> userIds = null;

		if (userCommentList != null) {
			userIds = new ArrayList<>();

			for (UserComment userComment : userCommentList) {
				userIds.add(userComment.getUserId());
			}
			commentFileList = fileDao.selectJoinCommentImageByProductIdAndUserId(productId, userIds);
		}

		if (commentFileList != null) {
			Map<Integer, List<FileCommentImage>> commentIdFileImageMap = new HashMap<>();
			for (FileCommentImage commentFile : commentFileList) {
			
				if (commentIdFileImageMap.get(commentFile.getReservationUserCommentId()) == null) {
					commentIdFileImageMap.put(commentFile.getReservationUserCommentId(), new ArrayList<>());
				}
				
				commentIdFileImageMap.get(commentFile.getReservationUserCommentId()).add(commentFile);

			}
			
			for (UserComment userComment : userCommentList) {
				if (commentIdFileImageMap.get(userComment.getId()) != null) {
					userComment.setCommentImageList(commentIdFileImageMap.get(userComment.getId()));
				}
			}

		}
		
		return userCommentList;
	}

	private CommentStats findCommentStatsByProductId(Integer productId) {
		return userCommentDao.selectStatsByProductId(productId);
	}
}
