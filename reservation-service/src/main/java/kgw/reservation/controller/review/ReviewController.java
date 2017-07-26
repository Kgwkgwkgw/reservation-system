package kgw.reservation.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
	@Value("${USER_DIR}")
	private String DIRNAME;
	@Autowired
	public ReviewController() {
	}

	@GetMapping("/form")
	public String form(@RequestParam Integer productId) {
		return DIRNAME+"/reviewWrite";
	}
}