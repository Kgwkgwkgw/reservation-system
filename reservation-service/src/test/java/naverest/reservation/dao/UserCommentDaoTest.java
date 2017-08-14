package naverest.reservation.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.config.RootApplicationContextConfig;
import naverest.reservation.domain.ReservationUserComment;
import naverest.reservation.domain.User;
import naverest.reservation.dto.CommentStats;
import naverest.reservation.dto.UserComment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class UserCommentDaoTest {
	
	@Autowired
	private UserCommentDao userCommentDao;
	@Autowired
	private UserDao userDao;
	private List<UserComment> userCommentList;
	
	private ReservationUserComment reservationUserComment;
	private User user;
	
	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setAdminFlag(0);
		user.setEmail("abc@naver.com");
		user.setNickname("하아...");
		user.setSnsType("Naver");
		Integer id = userDao.insert(user);
		user.setId(id);
		
		reservationUserComment = new ReservationUserComment();
		reservationUserComment.setComment("진짜 힘들어요...");
		reservationUserComment.setProductId(27);
		reservationUserComment.setScore(new BigDecimal(4.0));
		reservationUserComment.setUserId(user.getId());
		
	}
	
	@Test
	public void shouldSelectUserCommentByproductId() {
		Integer id = userCommentDao.insert(reservationUserComment);
		reservationUserComment.setId(id);
		
		userCommentList = userCommentDao.selectByProductId(reservationUserComment.getProductId(), 0, 10);
		System.out.println(userCommentList);
		assertThat(userCommentList, is(notNullValue()));
	}
	
	@Test
	public void shouldSelectStatsByProductId() {
		CommentStats commentStats = userCommentDao.selectStatsByProductId(27);
		BigDecimal averageScore = commentStats.getAverageScore();
		Integer count = commentStats.getCount();
		BigDecimal total = averageScore.multiply(new BigDecimal(count));
		
		Integer id = userCommentDao.insert(reservationUserComment);
		reservationUserComment.setId(id);
		
		CommentStats selectedCommentStats = userCommentDao.selectStatsByProductId(27);
		assertThat(selectedCommentStats.getAverageScore().setScale(2, RoundingMode.HALF_UP), is(total.add(reservationUserComment.getScore())
																  .divide(new BigDecimal(count+1))
																  .setScale(2, RoundingMode.HALF_UP)));
		assertThat(selectedCommentStats.getCount(), is(count+1));
	}

}
