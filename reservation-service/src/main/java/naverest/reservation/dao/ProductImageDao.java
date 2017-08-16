package naverest.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import naverest.reservation.dto.FileProductImage;
import naverest.reservation.jdbc.CustomNamedParameterJdbcTemplate;
import naverest.reservation.sql.ProductImageSqls;

@Repository
public class ProductImageDao {
	private CustomNamedParameterJdbcTemplate jdbc;
	private RowMapper<FileProductImage> fileProductImageRowMapper = BeanPropertyRowMapper.newInstance(FileProductImage.class);
	
	@Autowired
	public ProductImageDao(DataSource dataSource) {
		this.jdbc = new CustomNamedParameterJdbcTemplate(dataSource, ProductImageDao.class);
	}
	public List<FileProductImage> selectJoinFileByProductId(Integer productId){
		Map<String, Object> params = Collections.singletonMap("productId", productId);
		return jdbc.query(ProductImageSqls.SELECT_JOIN_FILE_BY_PRODUCT_ID, params, fileProductImageRowMapper);
    }
}
