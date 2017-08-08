package naverest.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import naverest.reservation.domain.ProductPrice;
import naverest.reservation.sql.ProductPriceSqls;

@Repository
public class ProductPriceDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductPrice> rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);
    private final Logger log = LoggerFactory.getLogger(ProductPriceDao.class);
    
    @Autowired
	public ProductPriceDao (DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
    
    public List<ProductPrice> selectPriceByProductId(Integer productId) {
    	Map<String, Object> params = Collections.singletonMap("productId", productId);
    		try {
    			return jdbc.query(ProductPriceSqls.SELECT_PRICE_BY_PRODUCT_ID, params, rowMapper);
    		} catch (DataAccessException e){
    			log.error("ProductPriceDao::selectPriceByProductId",e);
    			return null;
    		}
    }
}
