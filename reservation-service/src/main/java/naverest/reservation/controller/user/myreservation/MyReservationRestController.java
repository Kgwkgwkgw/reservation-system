package naverest.reservation.controller.user.myreservation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import naverest.reservation.annotation.LogginedUser;
import naverest.reservation.domain.User;
import naverest.reservation.dto.MyReservation;
import naverest.reservation.dto.MyReservationCount;
import naverest.reservation.service.ReservationInfoService;

@RestController
@RequestMapping("/api/myreservation")
public class MyReservationRestController {
	private ReservationInfoService reservationInfoService;
	private final Logger log = LoggerFactory.getLogger(MyReservationRestController.class);
	@Autowired
	public MyReservationRestController (ReservationInfoService reservationInfoService){
		this.reservationInfoService = reservationInfoService;
	}
	@GetMapping
	public List<MyReservation> getAllReservationList(@RequestParam(required=false) Integer type, @LogginedUser User user) {
		log.info("getList call===="+user.getId());
		if(type==null) {
			return reservationInfoService.findAllReservationByUserId(user.getId());
		}
		return reservationInfoService.findReservationByUserIdAndType(user.getId(), type);
	}
	
	@GetMapping("/count")
	public MyReservationCount getReservationCount(  @LogginedUser User user) {
		
		
		log.info("count call===="+user.getId());
		return reservationInfoService.countAllReservationByUserId(user.getId());
	}
	
	@DeleteMapping("/{reservationType}")
	@ResponseBody
	public Integer removeReservationById(@PathVariable Integer reservationType) {
		return reservationInfoService.removeReservationById(reservationType);
	}

}
