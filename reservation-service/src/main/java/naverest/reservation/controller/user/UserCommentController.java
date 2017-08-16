package naverest.reservation.controller.user;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import naverest.reservation.annotation.LogginedUser;
import naverest.reservation.domain.ReservationUserComment;
import naverest.reservation.domain.User;
import naverest.reservation.service.UserCommentService;

@Controller
@RequestMapping("/reviews")
public class UserCommentController {
	@Value("${naverest.userDir}")
	private String DIR_NAME;
	private final Logger log = LoggerFactory.getLogger(UserCommentController.class);
	private UserCommentService userCommentService;

	@Autowired
	public UserCommentController(UserCommentService userCommentService) {
		this.userCommentService = userCommentService;
	}
	
	@GetMapping
 	public String reviewView(@RequestParam Integer productId) {
 		return DIR_NAME+ "/review";
 	}
	
	@GetMapping("/form")
 	public String reviewForm(@RequestParam Integer productId) {
 		return DIR_NAME+ "/reviewWrite";
 	}
	
	@PostMapping("/form")
	public String makeReview(@ModelAttribute @Valid ReservationUserComment reservationUserComment,
			@RequestParam (required=false) List<Integer> fileIdList, @LogginedUser User user,
			RedirectAttributes redirectAttributes) {
		log.info("{}",reservationUserComment);
		
		Integer userId = user.getId();
		reservationUserComment.setUserId(userId);
		
		if(fileIdList!= null) {
			userCommentService.createUserCommentAndUserCommentImage(reservationUserComment, fileIdList);
		} else {
			userCommentService.createUserComment(reservationUserComment);
		}
		
		redirectAttributes.addAttribute("productId", reservationUserComment.getProductId());
		return "redirect:/reviews";
		
	}
	
}