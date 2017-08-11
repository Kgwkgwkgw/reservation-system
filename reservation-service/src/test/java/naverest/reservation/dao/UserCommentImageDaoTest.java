package naverest.reservation.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.config.RootApplicationContextConfig;
import naverest.reservation.domain.ReservationUserCommentImage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootApplicationContextConfig.class)
@Transactional
public class UserCommentImageDaoTest {
	
	@Autowired
	UserCommentImageDao userCommentImageDao;
	List<ReservationUserCommentImage> imageList;
	
	private final Logger log = LoggerFactory.getLogger(UserCommentImageDaoTest.class);

	
	@Before
	public void setUp() throws Exception{
		ReservationUserCommentImage reservationUserCommentImage  = new ReservationUserCommentImage();
		reservationUserCommentImage.setFileId(218);
		reservationUserCommentImage.setReservationUserCommentId(18);
		
		ReservationUserCommentImage reservationUserCommentImage2  = new ReservationUserCommentImage();
		reservationUserCommentImage.setFileId(219);
		reservationUserCommentImage.setReservationUserCommentId(18);
		
		imageList.add(reservationUserCommentImage);
		imageList.add(reservationUserCommentImage2);
		
	}
	
	@Test
	public void shouldInsertBatch() {
//		int howMany = userCommentImageDao.insertBatch(imageList);
//		log.info(""+howMany);
	}

}
