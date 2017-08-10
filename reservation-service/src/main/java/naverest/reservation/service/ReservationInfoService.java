package naverest.reservation.service;

import java.util.List;

import naverest.reservation.domain.ReservationInfo;
import naverest.reservation.dto.MyReservation;
import naverest.reservation.dto.MyReservationCount;

public interface ReservationInfoService {
	public ReservationInfo create(ReservationInfo reservationInfo);
	public List<MyReservation> findAllReservationByUserId(Integer userId);
	public List<MyReservation> findReservationByUserIdAndType(Integer userId, Integer type);
	public MyReservationCount countAllReservationByUserId(Integer userId);
	public Integer removeReservationById(Integer type);
	public Integer modifyTypeById(Integer id, Integer type);
	public Integer findCountByUserIdAndProductId(Integer productId, Integer userId) ;
}
