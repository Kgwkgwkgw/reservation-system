package naverest.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import naverest.reservation.domain.Category;
import naverest.reservation.jdbc.CustomNamedParameterJdbcTemplate;
import naverest.reservation.sql.CategorySqls;

@Repository
public class CategoryDao {
	private CustomNamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<Category> rowMapper = BeanPropertyRowMapper.newInstance(Category.class);
    
    @Autowired
	public CategoryDao (DataSource dataSource) {
		this.jdbc = new CustomNamedParameterJdbcTemplate(dataSource, CategoryDao.class);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("category")
                .usingGeneratedKeyColumns("id");
	}
    
    public Integer insert(Category category) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(category);
        return insertAction.executeAndReturnKey(params).intValue();
    }
    
    public Integer delete(Integer id) {
    		Map<String, ?> params = Collections.singletonMap("id", id);
        return jdbc.update(CategorySqls.DELETE_BY_ID, params);
    }
    
    public Integer update (Category category) {
    		SqlParameterSource params = new BeanPropertySqlParameterSource(category);
    		return jdbc.update(CategorySqls.UPDATE_BY_ID, params); 
    }

    public Category selectById (Integer id) {
    		Map<String, ?> params = Collections.singletonMap("id", id);
    		return jdbc.queryForObject(CategorySqls.SELECT_BY_ID, params, rowMapper);
    }
    
    public List<Category> selectAll () {
    		Map<String, Object> params = Collections.emptyMap();
    		return jdbc.query(CategorySqls.SELECT_ALL, params, rowMapper);
    }
    
}
