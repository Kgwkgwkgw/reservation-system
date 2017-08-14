package naverest.reservation.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import naverest.reservation.domain.Category;
import naverest.reservation.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	@Value("${naverest.adminDir}")
	private String DIR_NAME;
	
	private	final String CATEGORY_DIR = "/categories"; 
	private CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("list", categoryService.findAll());
		
		return DIR_NAME + CATEGORY_DIR+"/index";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("url", DIR_NAME+ CATEGORY_DIR);
		
		return DIR_NAME + CATEGORY_DIR+"/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Category category) {
		categoryService.create(category);
		return "redirect:"+DIR_NAME+ CATEGORY_DIR;
	}

}
