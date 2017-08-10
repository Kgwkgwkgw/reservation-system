package naverest.reservation.service;

import java.util.List;

import naverest.reservation.domain.Category;

public interface CategoryService {
	public Category create (Category category);
	public boolean delete(Integer id);
	
	public boolean update(Category category);
	public Category findById(Integer id);
	public Integer findByName(String name);
	public List<Category> findAll();
}
