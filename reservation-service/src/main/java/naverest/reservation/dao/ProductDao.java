package naverest.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import naverest.reservation.dto.ProductDetail;
import naverest.reservation.dto.ProductMain;
import naverest.reservation.dto.ProductReservation;
import naverest.reservation.jdbc.CustomNamedParameterJdbcTemplate;
import naverest.reservation.sql.ProductSqls;

@Repository
public class ProductDao {
	private CustomNamedParameterJdbcTemplate jdbc;
    private RowMapper<ProductMain> rowMapper = BeanPropertyRowMapper.newInstance(ProductMain.class);
    private RowMapper<ProductDetail> productDetailRowMapper = BeanPropertyRowMapper.newInstance(ProductDetail.class);
	private RowMapper<ProductReservation> productReservationRowMapper = BeanPropertyRowMapper.newInstance(ProductReservation.class);
	private final Logger log = LoggerFactory.getLogger(ProductDao.class);

    
    @Autowired
	public ProductDao (DataSource dataSource) {
		this.jdbc = new CustomNamedParameterJdbcTemplate(dataSource, ProductDao.class);
	}
    
    public List<ProductMain> selectAllProductMainLimit (Integer offset, Integer size) {
		Map<String, Object> params = new HashMap<>();
		params.put("offset", offset);
		params.put("size", size);
		return jdbc.query(ProductSqls.SELECT_ALL_PRODUCTMAIN_LIMIT, params, rowMapper);
    }
    
    public Integer countAll () {
    		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(ProductSqls.COUNT_ALL, params, Integer.class);
    }
    
    public List<ProductMain> selectProductMainByCategoryLimit (Integer categoryId, Integer offset, Integer size) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("offset", offset);
		params.put("size", size);
		return jdbc.query(ProductSqls.SELECT_PRODUCTMAIN_BY_CATEGORY_LIMIT, params, rowMapper);
    }
    
    public Integer countByCategory (Integer categoryId) {
    		Map<String, Object> params = Collections.singletonMap("categoryId", categoryId);
		return jdbc.queryForObject(ProductSqls.COUNT_BY_CATEGORY, params, Integer.class); 
    }
    
    public ProductDetail selectProductDetail (Integer id) {
    		Map<String, Object> params = Collections.singletonMap("id", id);
    		return jdbc.queryForObject(ProductSqls.SELECT_PRODUCT_DETAIL_BY_ID, params, productDetailRowMapper);	
    }
    
    public ProductReservation selectProductReservation (Integer id) {
		Map<String, Object> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(ProductSqls.SELECT_PRODUCT_RESERVATION_BY_ID, params, productReservationRowMapper);	
	}
    
}
