package naverest.reservation.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.config.RootApplicationContextConfig;
import naverest.reservation.domain.FileDomain;
import naverest.reservation.dto.FileProductImage;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class FileDaoTest {
	@Autowired
	FileDao fileDao;
	FileDomain fileDomain;
	
	@Test
	public void shouldSelectFileById(){
		fileDomain = fileDao.selectById(1);
		assertThat(fileDomain.getFileName(), is("abc.jpg"));
	}
	
	@Test
	public void shouldSelectJoinProductImageByProductId(){
		List<FileProductImage> fList = fileDao.selectJoinProductImageByProductId(27);
		assertThat(fList.size(), is(2));
		assertThat(fList.get(0).getId(), is(1));
		assertThat(fList.get(1).getId(), is(5));
		assertThat(fList.get(0).getSaveFileName(), is("/2017/07/17/abc.jpg"));
		assertThat(fList.get(1).getSaveFileName(), is("/2017/07/17/avatar3.png"));
		
	}
}
