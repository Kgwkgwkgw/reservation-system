package naverest.reservation.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import naverest.reservation.domain.ReservationUserComment;
import naverest.reservation.domain.ReservationUserCommentImage;
import naverest.reservation.dto.CommentStats;
import naverest.reservation.dto.FileCommentImage;
import naverest.reservation.dto.UserComment;
import naverest.reservation.dto.UserCommentWrapper;
import naverest.reservation.service.UserCommentService;

@Service
@Transactional(readOnly = true)
public class UserCommentServiceImpl implements UserCommentService{
	private FileDao fileDao;
	private UserCommentDao userCommentDao;
	private UserCommentImageDao userCommentImageDao;
	
	private final Logger log = LoggerFactory.getLogger(UserCommentServiceImpl.class);

	@Autowired
	public UserCommentServiceImpl(FileDao fileDao, UserCommentDao userCommentDao, UserCommentImageDao userCommentImageDao) {
		this.fileDao = fileDao;
		this.userCommentDao = userCommentDao;
		this.userCommentImageDao = userCommentImageDao;
	}
	@Override
	@Transactional(readOnly = false)
	public Integer createUserCommentAndUserCommentImage(ReservationUserComment reservationUserComment, List<Integer> fileIdList) {
		Integer commentId = addUserComment(reservationUserComment);
		List<ReservationUserCommentImage> reservationUserCommentImageList = new ArrayList<>();
		for (Integer fileId : fileIdList) {
			ReservationUserCommentImage reservationUserCommentImage = new ReservationUserCommentImage();
			reservationUserCommentImage.setReservationUserCommentId(commentId);
			reservationUserCommentImage.setFileId(fileId);
			reservationUserCommentImageList.add(reservationUserCommentImage);
		}
		userCommentImageDao.insertBatch(reservationUserCommentImageList);
		fileDao.updateByIds(fileIdList);
		return commentId;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Integer createUserComment(ReservationUserComment reservationUserComment) {
		return addUserComment(reservationUserComment);
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

	@Override
	@Transactional(readOnly = false)
	public Integer removeImage(Integer fileId) {
		log.info("user comment service fileId ="+fileId);
		return fileDao.deleteById(fileId);
	}

	private List<UserComment> findCommentListWithImage(Integer productId, Integer offset, Integer size) {
		List<UserComment> userCommentList = userCommentDao.selectByProductId(productId, offset, size);
		log.info("{}",userCommentList);
		List<FileCommentImage> commentFileList = null;
		List<Integer> userIds = null;

		if (userCommentList != null) {
			userIds = new ArrayList<>();

			for (UserComment userComment : userCommentList) {
				userIds.add(userComment.getUserId());
			}
			commentFileList = userCommentImageDao.selectJoinFileAndCommentByProductIdAndUserId(productId, userIds);
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
				if (commentIdFileImageMap.containsKey(userComment.getId())) {
					userComment.setCommentImageList(commentIdFileImageMap.get(userComment.getId()));
				}
			}

		}
		return userCommentList;
	}
	
	private CommentStats findCommentStatsByProductId(Integer productId) {
		return userCommentDao.selectStatsByProductId(productId);
	}
	
	private Integer addUserComment(ReservationUserComment reservationUserComment) {
		Date date = new Date();
		reservationUserComment.setCreateDate(date);
		reservationUserComment.setModifyDate(date);
		return userCommentDao.insert(reservationUserComment);
	}
}
