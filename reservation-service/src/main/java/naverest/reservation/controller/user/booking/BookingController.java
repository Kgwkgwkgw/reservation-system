package naverest.reservation.controller.user.booking;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import naverest.reservation.annotation.LogginedUser;
import naverest.reservation.annotation.ReservationForm;
import naverest.reservation.domain.ReservationInfo;
import naverest.reservation.domain.User;
import naverest.reservation.service.ProductService;
import naverest.reservation.service.ReservationInfoService;

@Controller
@RequestMapping("/booking")
public class BookingController {
	@Value("${naverest.userDir}")
	private String DIR_NAME;
	private ProductService productService;
	private ReservationInfoService reservationInfoService;
	private final Logger log = LoggerFactory.getLogger(BookingController.class);
	@Autowired
	public BookingController(ProductService productService, ReservationInfoService reservationInfoService) {
		this.productService = productService;
		this.reservationInfoService = reservationInfoService;
	}
	
	@GetMapping
	public String booking(@RequestParam Integer productId, Model model) {
		model.addAttribute("productReservation", productService.findProductReservation(productId));
		
		return DIR_NAME + "/booking";
	}
	
	@PostMapping
	public String booking(@ReservationForm @Valid ReservationInfo reservationInfo, @LogginedUser User user) {
		reservationInfo.setUserId(user.getId());
		reservationInfoService.create(reservationInfo);
		
		log.info("{}",reservationInfo);
		return "redirect:/myreservation";
	}
}
