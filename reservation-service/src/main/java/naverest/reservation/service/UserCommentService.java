package naverest.reservation.service;

import naverest.reservation.dto.UserCommentWrapper;

public interface UserCommentService {
	UserCommentWrapper findCommentWrapperByProductId(Integer productId, Integer offset, Integer size);
}
