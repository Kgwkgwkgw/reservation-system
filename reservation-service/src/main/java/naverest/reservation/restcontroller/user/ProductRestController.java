package naverest.reservation.restcontroller.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import naverest.reservation.dto.Criteria;
import naverest.reservation.dto.ProductMain;
import naverest.reservation.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
	private ProductService productService;

	@Autowired
	public ProductRestController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	@ResponseBody
	public List<ProductMain> readList(@RequestParam(required=false) Integer categoryId, @ModelAttribute @Valid Criteria criteria) {
		if(categoryId != null) {
			return productService.findProductMainByCategoryLimit(categoryId, criteria.getOffset(), criteria.getSize());
		}
		return productService.findAllProductMainLimit(criteria.getOffset(), criteria.getSize());
	}

	@GetMapping("/count")
	@ResponseBody
	public Integer countList(@RequestParam(required=false) Integer categoryId) {
		if(categoryId != null) {
			return productService.countByCategory(categoryId);
		}
		
		return productService.countAll();
	}
}
