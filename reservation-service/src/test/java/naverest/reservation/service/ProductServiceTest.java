package naverest.reservation.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.config.RootApplicationContextConfig;
import naverest.reservation.dao.ProductDao;
import naverest.reservation.dao.ProductImageDao;
import naverest.reservation.dto.FileProductImage;
import naverest.reservation.dto.ProductDetail;
import naverest.reservation.dto.ProductMain;
import naverest.reservation.service.impl.ProductServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ProductServiceTest {

	@Mock
	private ProductImageDao productImageDao;

	@Mock
	private ProductDao productDao;

	@InjectMocks
	private ProductServiceImpl productService;

	ProductMain productMain;
	ProductDetail productDetail;
	
	private static final Integer EXISTENCE = 1;
	private static final Integer NOT_EXISTENCE = 0;
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(productDao.selectProductDetail(EXISTENCE)).thenAnswer(new Answer<ProductDetail>() {
			@Override
			public ProductDetail answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				ProductDao mock = (ProductDao) invocation.getMock();
				Integer productId = (Integer) args[0];
				ProductDetail productDetail = new ProductDetail();
				productDetail.setId(productId);
				productDetail.setName("부수투캠프 재롱잔치");
				productDetail.setDescription("모두 나와 춤을 춥니다.");
				productDetail.setEvent("둘이 오면 공짜");
				productDetail.setContent("content is content");
				productDetail.setSalesFlag(1);
				productDetail.setHomepage("boostcamp.com");
				productDetail.setEmail("boostcamp@naver.com");
				productDetail.setTel("tel");
				productDetail.setPlaceName("placeName");
				productDetail.setPlaceLot("placeLog");
				productDetail.setPlaceStreet("placeStreet");

				return productDetail;
			}
		});
		when(productDao.selectProductDetail(NOT_EXISTENCE)).thenAnswer(new Answer<ProductDetail>() {
			@Override
			public ProductDetail answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				ProductDao mock = (ProductDao) invocation.getMock();
				return null;
			}
		});
		when(productImageDao.selectJoinFileByProductId(any())).thenAnswer(new Answer<List<FileProductImage> >() {
			@Override
			public List<FileProductImage> answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				ProductImageDao mock = (ProductImageDao) invocation.getMock();
				return null;
			}
		});
	}

	@Test
	public void shoudFindAllProductMainLimit() {

	}

	@Test
	public void shouldFindProductDetail() {
		ProductDetail selectedProductDetail = productService.findProductDetail(EXISTENCE);
		
		verify(productImageDao).selectJoinFileByProductId(selectedProductDetail.getId());
		verify(productDao).selectProductDetail(EXISTENCE);
		
	}
	
	@Test
	public void shouldNotFindProductDetail() {
		productService.findProductDetail(NOT_EXISTENCE);
		
		verify(productImageDao, never()).selectJoinFileByProductId(any());
		verify(productDao).selectProductDetail(NOT_EXISTENCE);

	}
}
