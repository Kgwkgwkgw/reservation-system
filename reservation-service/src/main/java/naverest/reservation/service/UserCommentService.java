package naverest.reservation.service;

import java.util.List;

import naverest.reservation.domain.ReservationUserComment;
import naverest.reservation.dto.CommentStats;
import naverest.reservation.dto.UserComment;
import naverest.reservation.dto.UserCommentWrapper;

public interface UserCommentService {
	public UserCommentWrapper getCommentListByProductId(Integer productId, Integer offset, Integer size);
	public List<UserComment> findCommentListWithImage(Integer productId, Integer offset, Integer size);
	public CommentStats findCommentStatsByProductId(Integer productId);
	public Integer createReservationUserComment(ReservationUserComment userComment, List<Integer> fileIdList);
	public Integer removeUserCommentImagefile(Integer fileId);
}
