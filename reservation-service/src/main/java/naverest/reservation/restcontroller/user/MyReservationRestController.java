package naverest.reservation.restcontroller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import naverest.reservation.annotation.LogginedUser;
import naverest.reservation.domain.User;
import naverest.reservation.dto.MyReservation;
import naverest.reservation.dto.MyReservationCount;
import naverest.reservation.service.ReservationInfoService;
import naverest.reservation.sql.ReservationInfoSqls;

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
	public MyReservationCount getReservationCount(@LogginedUser User user) {
		log.info("count call===="+user.getId());
		return reservationInfoService.countAllReservationByUserId(user.getId());
	}
	
	
	@DeleteMapping("/cancellation")
	public Integer removeByType(@LogginedUser User user) {
		return reservationInfoService.removeByType(user.getId(), ReservationInfoSqls.CANCELLATION);
	}
	
	@PutMapping("/{reservationId:[1-9]+[0-9]*}/cancellation")
	public Integer cancelById(@PathVariable Integer reservationId) {
		return reservationInfoService.modifyTypeById(reservationId, ReservationInfoSqls.CANCELLATION);
	}


}
