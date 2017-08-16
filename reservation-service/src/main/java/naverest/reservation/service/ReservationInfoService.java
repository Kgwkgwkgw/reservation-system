package naverest.reservation.service;

import java.util.List;

import naverest.reservation.domain.ReservationInfo;
import naverest.reservation.dto.MyReservation;
import naverest.reservation.dto.MyReservationCount;

public interface ReservationInfoService {
	ReservationInfo create(ReservationInfo reservationInfo);
	List<MyReservation> findAllReservationByUserId(Integer userId);
	List<MyReservation> findReservationByUserIdAndType(Integer userId, Integer type);
	MyReservationCount countAllReservationByUserId(Integer userId);
	Integer removeByType(Integer userId, String type);
	Integer modifyTypeById(Integer id, String type);
	Integer findCountByUserIdAndProductId(Integer productId, Integer userId) ;
}
