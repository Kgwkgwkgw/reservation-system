package naverest.reservation.controller.user.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import naverest.reservation.dto.Criteria;
import naverest.reservation.dto.UserCommentWrapper;
import naverest.reservation.service.UserCommentService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

	private UserCommentService userCommentService;
	private final Logger log = LoggerFactory.getLogger(ReviewRestController.class);
	
	@Autowired
	public ReviewRestController(UserCommentService userCommentService) {
		this.userCommentService = userCommentService;
	}
	
	@GetMapping
 	public UserCommentWrapper getList(@RequestParam Integer productId, @ModelAttribute Criteria criteria) {
		log.debug("{}", criteria);
		return userCommentService.getCommentListByProductId(productId, criteria.getOffset(), criteria.getSize());
	}
	
	

	
}
