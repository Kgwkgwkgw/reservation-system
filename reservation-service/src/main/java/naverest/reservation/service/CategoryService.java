package naverest.reservation.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.dao.CategoryDao;
import naverest.reservation.domain.Category;

@Service
@Transactional
public class CategoryService {
	private CategoryDao categoryDao;
	
	@Autowired
	public CategoryService(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public Category create (Category category) {
    		category.setId(categoryDao.insert(category));
    		return category;
	}
    
    public boolean delete(Integer id) {
        return categoryDao.delete(id) == 1 ? true : false;
    }
    
    public boolean update(Category category) {
        return categoryDao.update(category) == 1 ? true : false;
    }
    @Transactional(readOnly=true)
    public Category findById(Integer id) {
    		return categoryDao.selectById(id);
    }
    @Transactional(readOnly=true)
    public Integer findByName(String name) {
    		return categoryDao.selectByName(name);
    }
    @Transactional(readOnly=true)
    public Collection<Category> findAll() {
    		return categoryDao.selectAll();
    }
}
