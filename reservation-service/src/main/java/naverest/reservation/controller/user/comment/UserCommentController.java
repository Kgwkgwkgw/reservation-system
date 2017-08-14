package naverest.reservation.controller.user.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reviews")
public class UserCommentController {
	@Value("${naverest.userDir}")
	private String DIR_NAME;
	private final Logger log = LoggerFactory.getLogger(UserCommentController.class);

	@Autowired
	public UserCommentController() {;
	}
	
	@GetMapping
 	public String reviewView(@RequestParam Integer productId) {
 		return DIR_NAME+ "/review";
 	}
	
}