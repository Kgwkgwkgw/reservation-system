package naverest.reservation.service;

import java.util.List;

import naverest.reservation.domain.ReservationUserComment;
import naverest.reservation.dto.UserCommentWrapper;

public interface UserCommentService {
	UserCommentWrapper findCommentWrapperByProductId(Integer productId, Integer offset, Integer size);
	Integer removeUserCommentImage(Integer fileId);
	Integer createUserComment(ReservationUserComment userComment);
	Integer createUserCommentAndUserCommentImage(ReservationUserComment userComment, List<Integer> fileIdList);
}
