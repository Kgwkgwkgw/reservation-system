package naverest.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import naverest.reservation.domain.ProductPrice;
import naverest.reservation.jdbc.CustomNamedParameterJdbcTemplate;
import naverest.reservation.sql.ProductPriceSqls;

@Repository
public class ProductPriceDao {
	private CustomNamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductPrice> rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);
    
    @Autowired
	public ProductPriceDao (DataSource dataSource) {
		this.jdbc = new CustomNamedParameterJdbcTemplate(dataSource, ProductPriceDao.class);
	}
    
    public List<ProductPrice> selectByProductId(Integer productId) {
	    	Map<String, Object> params = Collections.singletonMap("productId", productId);
	    	return jdbc.query(ProductPriceSqls.SELECT_BY_PRODUCT_ID, params, rowMapper);
    }
}
