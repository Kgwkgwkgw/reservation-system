package naverest.reservation.controller.user.product;

import static junit.framework.TestCase.*;
import static org.hamcrest.CoreMatchers.*;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import naverest.reservation.dto.ProductMain;
import naverest.reservation.restcontroller.user.ProductRestController;
import naverest.reservation.service.ProductService;


@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
	
	@Mock
	private ProductService productService;
	@InjectMocks
	ProductRestController productRestController;
	
	MockMvc mvc;
    private static long id = 1L;

    @Before
    public void setUp() {
    	 MockitoAnnotations.initMocks(this);
    	 List<ProductMain> list = new ArrayList<ProductMain>();
    	 
    	 list.add(new ProductMain(100, "당신만영","노잼", 1, "/2017/07/17/abc.jpg", "샤롯데씨어터"));
    	 list.add(new ProductMain(101, "당신만일","핵잼", 1, "/2017/07/17/abc.jpg", "샤롯데씨어터"));
 
         this.mvc = MockMvcBuilders.standaloneSetup(productRestController)
                 .build();
         when(productService.countAll()).thenReturn(26);
         when(productService.findAllProductMainLimit(0, 10)).thenReturn(list);
    }

    @Test
    public void configTest(){
        assertTrue(true);
    }
    @Test
    public void testCountList() throws Exception{
        mvc.perform(
                get("/api/products/count") 
        ).andExpect(status().isOk()).andExpect(content().string("26"));
       
        verify(productService).countAll();
    }
    
    @Test
    public void testReadList() throws Exception{
    	
        mvc.perform(
                get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
         .andExpect(jsonPath("$[0].id",is(100)))
        .andExpect(jsonPath("$[0].name").value("당신만영"))
        .andExpect(jsonPath("$[0].description").value("노잼"))
        
        .andExpect(jsonPath("$[1].id",is(101)))
        .andExpect(jsonPath("$[1].name").value("당신만일"))
        .andExpect(jsonPath("$[1].description").value("핵잼"));
        
        
       
        verify(productService).findAllProductMainLimit(0, 10);
    }
}
