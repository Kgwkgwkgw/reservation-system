package naverest.reservation.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import naverest.reservation.domain.Category;
import naverest.reservation.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
	private CategoryService categoryService;
	
	@Autowired
	public CategoryRestController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public void update(@PathVariable Integer id, @Valid @RequestBody Category category) {
		category.setId(id);
		categoryService.update(category);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public void delete(@PathVariable Integer id) {
		categoryService.delete(id);
	}
}
