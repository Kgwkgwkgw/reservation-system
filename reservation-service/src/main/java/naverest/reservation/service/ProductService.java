package naverest.reservation.service;

import java.util.List;

import naverest.reservation.dto.ProductDetail;
import naverest.reservation.dto.ProductMain;
import naverest.reservation.dto.ProductReservation;

public interface ProductService {
	
	public List<ProductMain> findAllProductMainLimit(Integer offset, Integer size);
	public List<ProductMain> findProductMainByCategoryLimit(Integer categoryId, Integer offset, Integer size);
	public ProductDetail findProductDetail(Integer id);
	public ProductReservation findProductReservation(Integer id);
	public Integer countAll();
	public Integer countByCategory(Integer categoryId);
	
}
