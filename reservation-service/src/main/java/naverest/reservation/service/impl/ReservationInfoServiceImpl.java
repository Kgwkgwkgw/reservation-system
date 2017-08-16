package naverest.reservation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.dao.ReservationInfoDao;
import naverest.reservation.domain.ReservationInfo;
import naverest.reservation.dto.MyReservation;
import naverest.reservation.dto.MyReservationCount;
import naverest.reservation.service.ReservationInfoService;
import naverest.reservation.sql.ReservationInfoSqls;

@Service
public class ReservationInfoServiceImpl implements ReservationInfoService {
	private ReservationInfoDao reservationInfoDao;	
	
	@Autowired
	public ReservationInfoServiceImpl(ReservationInfoDao reservationInfoDao) {
		this.reservationInfoDao = reservationInfoDao;
	}
	@Override
	public ReservationInfo create(ReservationInfo reservationInfo) {
		Date date = new Date();
		
		reservationInfo.setCreateDate(date);
		reservationInfo.setModifyDate(date);

		reservationInfo.setId(reservationInfoDao.insert(reservationInfo));
		return reservationInfo;
	}
	@Transactional(readOnly=true)
	@Override
	public List<MyReservation> findAllReservationByUserId(Integer userId) {
		return reservationInfoDao.selectByUserId(userId);
	}
	@Transactional(readOnly=true)
	@Override
	public List<MyReservation> findReservationByUserIdAndType(Integer userId, Integer type) {
		return reservationInfoDao.selectByUserIdAndType(userId, type);
	}
	@Transactional(readOnly=true)
	@Override
	public MyReservationCount countAllReservationByUserId(Integer userId) {
		List<MyReservation> myReservation = reservationInfoDao.selectByUserId(userId);
		MyReservationCount myReservationCount = new MyReservationCount();
		int schduleCount = 0;
		int completionCount = 0;
		int cancelCount = 0;
		int refundCount = 0;
		int etc = 0;
		
		if(myReservation!=null) {
			for( MyReservation item : myReservation) {
				String type = item.getReservationType();
				if(type.equals(ReservationInfoSqls.ASKING) || type.equals(ReservationInfoSqls.CONFIRM)) {
					schduleCount++;
				} else if (type.equals(ReservationInfoSqls.COMPLETION)) {
					completionCount++;
				} else if (type.equals(ReservationInfoSqls.CANCELLATION)) {
					cancelCount++;
				} else if (type.equals(ReservationInfoSqls.REFUND) ) {
					refundCount++;
				} else {
					etc++;
				}
			}
			myReservationCount.setSchedule(schduleCount);
			myReservationCount.setCompletion(completionCount);
			myReservationCount.setCancellationAndRefund(cancelCount+refundCount);
			myReservationCount.setTotal(schduleCount + completionCount + cancelCount+ refundCount+ etc);
		} else {
			myReservationCount.setSchedule(0);
			myReservationCount.setCompletion(0);
			myReservationCount.setCancellationAndRefund(0);
			myReservationCount.setTotal(0);
		}
		return myReservationCount;
	}
	@Override
	public Integer removeByType(Integer userId, String type) {
		return reservationInfoDao.deleteByType(userId, type);
	}
	@Override
	public Integer modifyTypeById(Integer id, String type) {
		return reservationInfoDao.updateTypeById(id, type);
	}
	@Transactional(readOnly=true)
	@Override
	public Integer findCountByUserIdAndProductId(Integer productId, Integer userId) {
		return reservationInfoDao.selectCountByUserIdAndProductId(productId, userId);
	}
	
}
