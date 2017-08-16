package naverest.reservation.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import naverest.reservation.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	private ProductService productService;
	@Value("${naverest.userDir}")
	private String DIR_NAME;
	private final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/{id:[1-9]+[0-9]*}")
	public String detail(@PathVariable Integer id, Model model) {
		model.addAttribute("product", productService.findProductDetail(id)); 
		
		return DIR_NAME+"/detail";
	}
	
}
