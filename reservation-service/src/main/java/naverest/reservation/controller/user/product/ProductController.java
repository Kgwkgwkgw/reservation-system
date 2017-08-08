package naverest.reservation.controller.user.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import naverest.reservation.domain.ReservationInfo;
import naverest.reservation.domain.User;
import naverest.reservation.security.LogginedUser;
import naverest.reservation.security.ReservationForm;
import naverest.reservation.service.ProductService;
import naverest.reservation.service.ReservationInfoService;

@Controller
@RequestMapping("/products")
public class ProductController {
	private ProductService productService;
	private ReservationInfoService reservationInfoService;
	@Value("${naverest.userDir}")
	private String DIRNAME;
	private static final String url = "/products";
	private final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	public ProductController(ProductService productService, ReservationInfoService reservationInfoService) {
		this.productService = productService;
		this.reservationInfoService = reservationInfoService;
	}
	
	@GetMapping("/{id}")
	public String detailPage(@PathVariable Integer id, Model model) {
		model.addAttribute("product", productService.findProductDetail(id));
		model.addAttribute("reservationUrl", url+"/reservation/"+id);
		
		return DIRNAME+"/detail";
	}
	
	@GetMapping("/reservation/{id}")
	public String reserveForm(@PathVariable Integer id, Model model) {
		model.addAttribute("productReservation", productService.findProductReservation(id));
		model.addAttribute("url", url+"/reservation"+"/"+id);

		return DIRNAME+"/reserve";
	}
	
	@PostMapping("/reservation/{id}")
	public String reserve(@PathVariable(value="id") Integer productId, Model model, @ReservationForm ReservationInfo reservationInfo, 
			@LogginedUser User user) {
		reservationInfo.setProductId(productId);
		reservationInfo.setUserId(user.getId());
				
		log.info("postInfo : {}",reservationInfo);
		
		reservationInfoService.create(reservationInfo);
		
		return "redirect:/users";
	}
	
	
}
