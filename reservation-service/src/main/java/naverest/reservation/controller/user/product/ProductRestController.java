package naverest.reservation.controller.user.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	public List<ProductMain> readList(@RequestParam(required=false) Integer categoryId, @RequestParam(required=false) Integer offset, @RequestParam(required=false) Integer size) {
		if(offset == null || size == null ) {
			offset = 0;
			size = 10;
		}
		
		if(categoryId != null) {
			return productService.findProductMainByCategoryLimit(categoryId, offset, size);
		}
		return productService.findAllProductMainLimit(offset, size);
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
