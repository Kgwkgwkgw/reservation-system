
package naverest.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import naverest.reservation.dao.FileDao;
import naverest.reservation.dao.ProductDao;
import naverest.reservation.dto.FileProductImage;
import naverest.reservation.dto.ProductDetail;
import naverest.reservation.dto.ProductMain;
import naverest.reservation.service.ProductService;

@Service
@Transactional(readOnly=true)
public class ProductServiceImpl implements ProductService {
	private ProductDao productDao;
	private FileDao fileDao;

	@Autowired
	public ProductServiceImpl(ProductDao productDao,  FileDao fileDao) {
		this.productDao = productDao;
		this.fileDao = fileDao;
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
		List<FileProductImage> productFileList = fileDao.selectJoinProductImageByProductId(id);
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
	
	
}