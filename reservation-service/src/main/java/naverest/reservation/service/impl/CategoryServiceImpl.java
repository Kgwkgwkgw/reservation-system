package naverest.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.dao.CategoryDao;
import naverest.reservation.domain.Category;
import naverest.reservation.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService  {
	private CategoryDao categoryDao;
	
	@Autowired
	public CategoryServiceImpl(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	@Override
	public Category create (Category category) {
    		category.setId(categoryDao.insert(category));
    		return category;
	}
    
	@Override
    public boolean delete(Integer id) {
        return categoryDao.delete(id) == 1 ? true : false;
    }
    
	@Override
    public boolean update(Category category) {
        return categoryDao.update(category) == 1 ? true : false;
    }
	@Override
    @Transactional(readOnly=true)
    public Category findById(Integer id) {
		if(id ==null || id == 0) {
			return null;
		}
    		return categoryDao.selectById(id);
    }
	@Override
    @Transactional(readOnly=true)
    public List<Category> findAll() {
    		return categoryDao.selectAll();
    }
}
