
package naverest.reservation.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.dao.FileDao;
import naverest.reservation.dao.ProductDao;
import naverest.reservation.dao.ProductPriceDao;
import naverest.reservation.domain.ProductPrice;
import naverest.reservation.dto.FileImage;
import naverest.reservation.dto.ProductDetail;
import naverest.reservation.dto.ProductMain;
import naverest.reservation.dto.ProductReservation;
import naverest.reservation.dto.UserCommentWrapper;

@Service
@Transactional(readOnly=true)
public class ProductService {
	private ProductDao productDao;
	private UserCommentService userCommentService;
	private FileDao fileDao;
	private ProductPriceDao productPriceDao;

	@Autowired
	public ProductService(ProductDao productDao, UserCommentService userCommentService, FileDao fileDao,
							ProductPriceDao productPriceDao) {
		this.productDao = productDao;
		this.userCommentService = userCommentService;
		this.fileDao = fileDao;
		this.productPriceDao = productPriceDao;
	}
	
	public List<ProductMain> findAllLimit(Integer offset, Integer size) {
		return productDao.selectAllLimit(offset, size);
	}
	
	public List<ProductMain> findByCategoryLimit(Integer categoryId, Integer offset, Integer size) {
		return productDao.selectByCategoryLimit(categoryId, offset, size);
	}
	
	public ProductDetail findProductDetail(Integer id) {
		
		ProductDetail productDetail = productDao.selectProductDetail(id);
		if(productDetail==null)
			return null;
		Collection<FileImage> productFileList = fileDao.selectJoinProductImageByProductId(id);
		//평균점수와 코멘트 전체 개수 설정 
		UserCommentWrapper userCommentWrapper = userCommentService.getCommentListByProductId(id, 0, 3);
		productDetail.setFileList(productFileList);
		productDetail.setUserCommentWrapper(userCommentWrapper);
		return productDetail;
	}
	
	public ProductReservation findProductReservation(Integer id) {
		ProductReservation productReservation = productDao.selectProductReservation(id);
		List<ProductPrice> productPriceList = productPriceDao.selectPriceByProductId(id);
		
		productReservation.setProductPriceList(productPriceList);
		return productReservation;
	}
	
	public Integer countAll() {
		return productDao.countAll();
	}
	
	public Integer countByCategory(Integer categoryId) {
		return productDao.countByCategory(categoryId);
	}
	
	
}