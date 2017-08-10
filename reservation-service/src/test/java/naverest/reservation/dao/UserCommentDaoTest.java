package naverest.reservation.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.config.RootApplicationContextConfig;
import naverest.reservation.domain.ReservationUserComment;
import naverest.reservation.dto.CommentStats;
import naverest.reservation.dto.UserComment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class UserCommentDaoTest {
	@Autowired
	UserCommentDao userCommentDao;
	List<UserComment> userCommentList;
	CommentStats commentStats;
	@Test
	public void shouldSelectUserCommentByProductId() {
		//insert
		ReservationUserComment reservationUserComment = new ReservationUserComment();
		reservationUserComment.setId(1001);
		reservationUserComment.setComment("bbbbbb");
		reservationUserComment.setProductId(27);
		reservationUserComment.setScore(4.0);
		reservationUserComment.setUserId(7);
		Integer id = userCommentDao.insert(reservationUserComment);
		reservationUserComment.setId(id);
		//check
		userCommentList = userCommentDao.selectUserCommentByProductId(27, 0, 10);
		assertThat(userCommentList.get(0).getProductName(), is("뮤직드라마 - 당신만이"));
		assertThat(userCommentList.get(0).getUsername(), is("****"));
		assertThat(userCommentList.get(0).getComment(), is("bbbbbb"));
		assertThat(userCommentList.get(0).getUserId(), is(7));
		assertThat(userCommentList.get(0).getScore(), is(4.0));
	}
	
	@Test
	public void shouldSelectStatsByProductId(){
		//before
		commentStats = userCommentDao.selectStatsByProductId(27);
		assertThat(commentStats.getAverageScore(), is(3.5) );
		assertThat(commentStats.getCount(), is(6));
		//insert
		ReservationUserComment reservationUserComment = new ReservationUserComment();
		reservationUserComment.setId(1001);
		reservationUserComment.setComment("bbbbbb");
		reservationUserComment.setProductId(27);
		reservationUserComment.setScore(3.5);
		reservationUserComment.setUserId(7);
		Integer id = userCommentDao.insert(reservationUserComment);
		reservationUserComment.setId(id);
		//check
		commentStats = userCommentDao.selectStatsByProductId(27);
		assertThat(commentStats.getAverageScore(), is(3.5) );
		assertThat(commentStats.getCount(), is(7));
	}

}
