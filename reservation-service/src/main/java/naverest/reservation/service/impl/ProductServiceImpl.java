
package naverest.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.dao.ProductDao;
import naverest.reservation.dao.ProductImageDao;
import naverest.reservation.dao.ProductPriceDao;
import naverest.reservation.domain.ProductPrice;
import naverest.reservation.dto.FileProductImage;
import naverest.reservation.dto.ProductDetail;
import naverest.reservation.dto.ProductMain;
import naverest.reservation.dto.ProductReservation;
import naverest.reservation.service.ProductService;

@Service
@Transactional(readOnly=true)
public class ProductServiceImpl implements ProductService {
	private ProductDao productDao;
	private ProductImageDao productImageDao;
	private ProductPriceDao productPriceDao;
	
	@Autowired
	public ProductServiceImpl(ProductDao productDao,  ProductImageDao productImageDao,
			ProductPriceDao productPriceDao) {
		this.productDao = productDao;
		this.productImageDao = productImageDao;
		this.productPriceDao = productPriceDao;
	}
	
	@Override
	public List<ProductMain> findAllProductMainLimit(Integer offset, Integer size) {
		return productDao.selectAllProductMainLimit(offset, size);
	}
	@Override
	public List<ProductMain> findProductMainByCategoryLimit(Integer categoryId, Integer offset, Integer size) {
		return productDao.selectProductMainByCategoryLimit(categoryId, offset, size);
	}
	@Override
	public ProductDetail findProductDetail(Integer id) {
		ProductDetail productDetail = productDao.selectProductDetail(id);
		if(productDetail==null)
			return null;
		List<FileProductImage> productFileList = productImageDao.selectJoinFileByProductId(id);
		productDetail.setFileList(productFileList);
		return productDetail;
	}
	@Override
	public Integer countAll() {
		return productDao.countAll();
	}
	@Override
	public Integer countByCategory(Integer categoryId) {
		return productDao.countByCategory(categoryId);
	}

	@Override
	public ProductReservation findProductReservation(Integer id) {
		ProductReservation productReservation = productDao.selectProductReservation(id);
		List<ProductPrice> productPriceList = productPriceDao.selectByProductId(id);
		
		productReservation.setProductPriceList(productPriceList);
		return productReservation;
	}
	
	
}