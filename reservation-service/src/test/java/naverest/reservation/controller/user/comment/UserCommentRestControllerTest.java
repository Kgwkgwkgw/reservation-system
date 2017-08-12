package naverest.reservation.controller.user.comment;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

		CommentStats commentStats = new CommentStats();
		commentStats.setAverageScore(3.8);
		commentStats.setCount(2);
		
		 List<UserComment> userCommentList = new ArrayList<UserComment>();
		 UserComment commentA = new UserComment();
		 commentA.setId(1);
		 commentA.setUserId(7);
		 commentA.setUsername("정현우");
		 commentA.setProductName("당신만이");
		 commentA.setReservationDate("2017-07-31");
		 commentA.setComment("나 이거 왜 봤나 모르겠다.");
		 
		 List<FileCommentImage> commentImageListA = new ArrayList<FileCommentImage>();
		 commentImageListA.add(new FileCommentImage(1, 1, "abc.jpg", 7));
		 commentA.setCommentImageList(commentImageListA);
		
		 UserComment commentB = new UserComment();
		 commentB.setId(2);
		 commentB.setUserId(8);
		 commentB.setUsername("강준호");
		 commentB.setProductName("당신만");
		 commentB.setReservationDate("2017-08-02");
		 commentB.setComment("시간떄우기용");
		
		 UserComment commentC = new UserComment();
		 commentC.setId(3);
		 commentC.setUserId(9);
		 commentC.setUsername("김길우");
		 commentC.setProductName("당신만이");
		 commentC.setReservationDate("2017-08-07");
		 commentC.setComment("진짜 너무 힘들었어요..");
		 
		 List<FileCommentImage> commentImageListC = new ArrayList<FileCommentImage>();
		 commentImageListC.add(new FileCommentImage(10, 3, "ccd.png", 9));
		 commentImageListC.add(new FileCommentImage(11, 3, "55me.png", 9));
		 commentC.setCommentImageList(commentImageListC);

		 UserComment commentD = new UserComment();
		 commentD.setId(4);
		 commentD.setUserId(9);
		 commentD.setUsername("김길우");
		 commentD.setProductName("당신만이");
		 commentD.setReservationDate("2017-08-08");
		 commentD.setComment("언제 끝날지..계속 기다렸어요.");
		
		 userCommentList.add(commentA);
		 userCommentList.add(commentB);
		 userCommentList.add(commentC);
		 userCommentList.add(commentD);
		
		 commentWrapperList.setCommentStats(commentStats);
		 commentWrapperList.setUserCommentList(userCommentList);
		


		this.mvc = MockMvcBuilders.standaloneSetup(userCommentRestController).build();

		when(userCommentService.findCommentWrapperByProductId(any(), 0, 10)).thenReturn(commentWrapperList);
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
		verify(userCommentService).findCommentWrapperByProductId(27, 0, 10);
	}

}

