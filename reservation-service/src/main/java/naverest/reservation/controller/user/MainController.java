package naverest.reservation.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import naverest.reservation.service.CategoryService;

@Controller
@RequestMapping("/")
public class MainController {
	@Value("${naverest.userDir}")
	private String DIRNAME;
	private CategoryService categoryService;

	@Autowired
	public MainController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("categoryList", categoryService.findAll());
		return DIRNAME+"/mainpage";
	}
}
