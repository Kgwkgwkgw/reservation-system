package naverest.reservation.controller.admin;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import naverest.reservation.domain.Category;
import naverest.reservation.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	@Value("${naverest.adminDir}")
	private String DIRNAME;
	
	private	final String CATEGORYDIR = "/categories"; 
	
	CategoryService categoryService;
	@Autowired
    ServletContext context;
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("list", categoryService.findAll());
		
		return DIRNAME + CATEGORYDIR+"/index";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("url", DIRNAME+ CATEGORYDIR);
		
		return DIRNAME + CATEGORYDIR+"/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Category category, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("url", DIRNAME+ CATEGORYDIR);
			model.addAttribute("error","빈 값은 넣을수 없습니다.");
			return DIRNAME + CATEGORYDIR+"/form";
		}
		
		categoryService.create(category);
		return "redirect:"+DIRNAME+ CATEGORYDIR;
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
