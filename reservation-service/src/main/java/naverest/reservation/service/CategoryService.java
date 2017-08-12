package naverest.reservation.service;

import java.util.List;

import naverest.reservation.domain.Category;

public interface CategoryService {
	Category create (Category category);
	boolean delete(Integer id);
	boolean update(Category category);
	Category findById(Integer id);
	List<Category> findAll();
}
