package naverest.reservation.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.config.RootApplicationContextConfig;
import naverest.reservation.dto.ProductDetail;
import naverest.reservation.dto.ProductMain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ProductDaoTest {

	@Autowired
	ProductDao productDao;
	ProductMain productMain;
	ProductDetail productDetail;
	@Test
	public void shoudSelectAllProductMainLimit(){
		
	}
	@Test
	public void shouldSelectProductDetail(){
		ProductDetail product = productDao.selectProductDetail(27);
		assertThat(product.getId(), is(27));
		assertThat(product.getName(), is("뮤직드라마 - 당신만이"));
		assertThat(product.getDescription(), is("당신만이 사랑입니다♥ 버럭쟁이 봉식이와 변덕쟁이 필례의 좌충우돌 인생STORY!!!"));
		assertThat(product.getHomepage(), is("http://www.charlottetheater.co.kr/"));
		assertThat(product.getPlaceName(), is("샤롯데 씨어터"));
		assertThat(product.getEmail(), is("kgwaaa@naver.com"));
		assertThat(product.getTel(), is("031952432"));
		assertThat(product.getContent(), is("컨텐쯔"));
	}
}
