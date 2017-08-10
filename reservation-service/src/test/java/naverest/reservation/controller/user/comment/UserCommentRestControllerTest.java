package naverest.reservation.controller.user.comment;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import naverest.reservation.controller.user.comment.UserCommentRestController;
import naverest.reservation.dto.CommentStats;
import naverest.reservation.dto.Criteria;
import naverest.reservation.dto.FileCommentImage;
import naverest.reservation.dto.UserComment;
import naverest.reservation.dto.UserCommentWrapper;
import naverest.reservation.service.UserCommentService;

@RunWith(MockitoJUnitRunner.class)
public class UserCommentRestControllerTest {

	@Mock
	private UserCommentService userCommentService;
	@InjectMocks
	UserCommentRestController userCommentRestController;

	MockMvc mvc;
	private static long id = 1L;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		UserCommentWrapper commentWrapperList = new UserCommentWrapper();

		// set CommentStats in commentWrapperList
		CommentStats commentStats = new CommentStats();
		commentStats.setAverageScore(3.8);
		commentStats.setCount(2);

		// init userCommentList in commentWrapperList
		 List<UserComment> userCommentList = new ArrayList<UserComment>();
		// set commentA in a userCommentList
		 UserComment commentA = new UserComment(1, 7, "정현우", "당신만이", 3.0, "나 이거 왜봤나 모르겠다", "2017-07-31");
		// 1 imagefile with commentA
		 List<FileCommentImage> commentImageListA = new ArrayList<FileCommentImage>();
		 commentImageListA.add(new FileCommentImage(1, 1, "abc.jpg", 7));
		 commentA.setCommentImageList(commentImageListA);
		
		// no image with commentB
		 UserComment commentB = new UserComment(2, 8, "강준호", "당신만이", 4.0, "시간때우기용",
		 "2017-08-02");
		
		// 2 imagefile with commentC
		 UserComment commentC = new UserComment(3, 9, "김길우", "당신만이", 5.0, "근래 보기드문수작", "2017-08-07");
		 List<FileCommentImage> commentImageListC = new ArrayList<FileCommentImage>();
		 commentImageListC.add(new FileCommentImage(10, 3, "ccd.png", 9));
		 commentImageListC.add(new FileCommentImage(11, 3, "55me.png", 9));
		 commentC.setCommentImageList(commentImageListC);
		// no image with commentD
		 UserComment commentD = new UserComment(4, 9, "김길우", "당신만이", 5.0, "근래 보기드문수작2", "2017-08-08");
		
		// push to userCommentList
		 userCommentList.add(commentA);
		 userCommentList.add(commentB);
		 userCommentList.add(commentC);
		 userCommentList.add(commentD);
		
		 commentWrapperList.setCommentStats(commentStats);
		 commentWrapperList.setUserCommentList(userCommentList);
		


		this.mvc = MockMvcBuilders.standaloneSetup(userCommentRestController).build();

		when(userCommentService.getCommentListByProductId(27, 0, 10)).thenReturn(commentWrapperList);
	}

	@Test
	public void configTest() {
		assertTrue(true);
	}

	@Test
	public void testCountList() throws Exception {
		Criteria criteria = new Criteria();
		criteria.setOffset(0);
		criteria.setSize(10);
		mvc.perform(get("/api/comments").param("productId", "27")
										.param("offset", "0")
										.param("size", "10"))
										.andExpect(status().isOk())
										.andExpect(jsonPath("$.commentStats.count").value(2))
										.andExpect(jsonPath("$.commentStats.averageScore").value(3.8))
										.andExpect(jsonPath("$.userCommentList.[0].id").value(1))
										.andExpect(jsonPath("$.userCommentList.[0].username").value("정현우"))

										;
		verify(userCommentService).getCommentListByProductId(27, 0, 10);
	}

}
//		//comment 1st
//		ReservationUserComment reservationUserCommentA = new ReservationUserComment();
//		reservationUserCommentA.setProductId(27);
//		reservationUserCommentA.setUserId(7);
//		reservationUserCommentA.setScore(4.0);
//		reservationUserCommentA.setComment("노잼");
//		List<Integer> fileIdListA = new ArrayList<Integer>();
//		fileIdListA.add(1);
//		fileIdListA.add(2);
//		fileIdListA.add(3);
//		userCommentService.createReservationUserComment(reservationUserCommentA, fileIdListA);
//
//		// comment 2nd
//		ReservationUserComment reservationUserCommentB = new ReservationUserComment();
//		reservationUserCommentB.setProductId(27);
//		reservationUserCommentB.setUserId(8);
//		reservationUserCommentB.setScore(4.0);
//		reservationUserCommentB.setComment("hack노잼");
//		List<Integer> fileIdListB = new ArrayList<Integer>();
// 		userCommentService.createReservationUserComment(reservationUserCommentB, fileIdListB);
//
//		commentWrapperList.setCommentStats(commentStats);

