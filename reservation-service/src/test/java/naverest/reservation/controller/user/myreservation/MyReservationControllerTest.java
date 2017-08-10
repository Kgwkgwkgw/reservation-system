package naverest.reservation.controller.user.myreservation;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import naverest.reservation.domain.User;
import naverest.reservation.service.ReservationInfoService;

@RunWith(MockitoJUnitRunner.class)
public class MyReservationControllerTest {

	@Mock
	private ReservationInfoService userCommentService;
	@InjectMocks
	MyReservationRestController myReservationRestController;

	MockMvc mvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mvc = MockMvcBuilders.standaloneSetup(myReservationRestController).build();
		
		User user = new User();
//		when(userCommentService.getReservationCount(user));
	}
}
