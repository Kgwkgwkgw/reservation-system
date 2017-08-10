package naverest.reservation.controller.user.login;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import naverest.reservation.oauth.naver.NaverLoginApiService;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
	
	@Mock
	private NaverLoginApiService naverLoginApiService;
	
	@InjectMocks
	private LoginController loginController;
	
	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
		
//		when(naverLoginApiService.reqAccessToken(oauthState, code, state))
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
