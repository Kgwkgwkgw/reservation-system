package naverest.reservation.controller.user.myreservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import naverest.reservation.sql.ReservationInfoSqls;

@Controller
@RequestMapping("/myreservation")
public class MyReservationController {
	@Value("${naverest.userDir}")
	private String DIRNAME;
	private final Logger log = LoggerFactory.getLogger(MyReservationController.class);

	@Autowired
	public MyReservationController() {
	}

	@GetMapping
	public String myPage(Model model) {
		model.addAttribute("asking", ReservationInfoSqls.ASKING);
		model.addAttribute("confirm", ReservationInfoSqls.CONFIRM);
		model.addAttribute("completion", ReservationInfoSqls.COMPLETION);
		model.addAttribute("cancellation", ReservationInfoSqls.CANCELLATION);
		model.addAttribute("refund", ReservationInfoSqls.REFUND);
		
		return DIRNAME+"/myreservation";
	}
	
	
}
