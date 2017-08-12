package naverest.reservation.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.config.RootApplicationContextConfig;
import naverest.reservation.dao.FileDao;
import naverest.reservation.dao.UserDao;
import naverest.reservation.domain.FileDomain;
import naverest.reservation.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class FileServiceTest {
	
	@Autowired
	FileDao fileDao;
	
	@Autowired
	UserDao userDao;

	FileDomain fileDomain;
	
	@Before
	public void setUp() {
		User user = new User();
		user.setAdminFlag(0);
		Integer userId = userDao.insert(user);
		
		fileDomain = new FileDomain();
		fileDomain.setUserId(userId);
		fileDomain.setFileName("abc.jpg");
		fileDomain.setSaveFileName("/2017/07/17/abc.jpg");
		fileDomain.setFileLength(997);
		fileDomain.setContentType("image/jpeg");
		fileDomain.setDeleteFlag(1);
		Date current = new Date();
		fileDomain.setCreateDate(current);
		fileDomain.setModifyDate(current);
	}
	@Test
	public void shouldFindFileById(){
		FileDomain insertedFileDomain = fileDao.insert(fileDomain);
		FileDomain selectedFileDomain = fileDao.selectById(insertedFileDomain.getId());
		assertThat(selectedFileDomain.getId(), is(insertedFileDomain.getId()));
		assertThat(selectedFileDomain.getFileName(), is(insertedFileDomain.getFileName()));
		assertThat(selectedFileDomain.getSaveFileName(), is(insertedFileDomain.getSaveFileName()));
		assertThat(selectedFileDomain.getContentType(), is(insertedFileDomain.getContentType()));
	}
}
