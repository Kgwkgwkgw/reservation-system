package naverest.reservation.service;

import java.util.List;

import naverest.reservation.dto.ProductDetail;
import naverest.reservation.dto.ProductMain;
import naverest.reservation.dto.ProductReservation;

public interface ProductService {
	List<ProductMain> findAllProductMainLimit(Integer offset, Integer size);
	List<ProductMain> findProductMainByCategoryLimit(Integer categoryId, Integer offset, Integer size);
	ProductDetail findProductDetail(Integer id);
	ProductReservation findProductReservation(Integer id);
	Integer countAll();
	Integer countByCategory(Integer categoryId);
	
}
