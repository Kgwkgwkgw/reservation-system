package kgw.reservation.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.config.RootApplicationContextConfig;
import naverest.reservation.dao.CategoryDao;
import naverest.reservation.domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class CategoryDaoTest {
	
	@Autowired
	CategoryDao categoryDao;
	Category category; 
	
	@Before
	public void setUp() throws Exception {
		category = new Category("공연");
	}
	
	@Test
	public void shouldInsertAndSelect() {
		int id = categoryDao.insert(category);
		
		Category selected = categoryDao.selectById(id);
		assertThat(selected.getName(), is(category.getName()));
	}
	
	@Test
	public void shouldInsertAndSelectByName() {
		int id = categoryDao.insert(category);
		category.setId(id);
		
		Integer selectedId = categoryDao.selectByName(category.getName());
		assertThat(selectedId, is(category.getId()));
	}
	
	@Test
	public void shouldDeleteById() {
		int id = categoryDao.insert(category); 
		int affected = categoryDao.delete(id);
		assertThat(affected, is(1));
	}
	
	@Test
	public void shouldSelectAll() {
		Collection<Category> allCategorys = categoryDao.selectAll();
		
		assertThat(allCategorys, is(notNullValue()));
	}
	
	@Test 
	public void shouldUpdateById() {
		int id = categoryDao.insert(category);
		category.setId(id);
		category.setName("전시");
		
		assertThat(categoryDao.update(category), is(1));
	}
	

}
