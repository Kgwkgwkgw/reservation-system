package naverest.reservation.controller.user.comment;

import java.util.List;

import javax.servlet.http.HttpSession;
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

import naverest.reservation.domain.ReservationUserComment;
import naverest.reservation.domain.User;
import naverest.reservation.service.ReservationInfoService;
import naverest.reservation.service.UserCommentService;

@Controller
@RequestMapping("/reviews")
public class UserCommentController {
	@Value("${naverest.userDir}")
	private String DIRNAME;
	private UserCommentService userCommentService;
	private ReservationInfoService reservationInfoService;
	private final Logger log = LoggerFactory.getLogger(UserCommentController.class);

	@Autowired
	public UserCommentController(UserCommentService userCommentService, 
			ReservationInfoService reservationInfoService) {
		this.userCommentService = userCommentService;
		this.reservationInfoService = reservationInfoService;
	}
	
	@GetMapping
 	public String reviewView(@RequestParam Integer productId) {
 		return DIRNAME+ "/review";
 	}
	
}