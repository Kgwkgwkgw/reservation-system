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
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import naverest.reservation.domain.FileDomain;
import naverest.reservation.dto.FileProductImage;
import naverest.reservation.sql.FileSqls;

@Repository
public class FileDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
    private RowMapper<FileProductImage> fileProductImageRowMapper = BeanPropertyRowMapper.newInstance(FileProductImage.class);

    private RowMapper<FileDomain> fileRowMapper = BeanPropertyRowMapper.newInstance(FileDomain.class);
	private final Logger log = LoggerFactory.getLogger(FileDao.class);

    @Autowired
    public FileDao(DataSource dataSource) {
    		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    		this.insertAction = new SimpleJdbcInsert(dataSource)
    				.withTableName("file")
    				.usingGeneratedKeyColumns("id");

    }
    
    public List<FileProductImage> selectJoinProductImageByProductId(Integer productId){
		Map<String, Object> params = Collections.singletonMap("productId", productId);
		
		try {
    		return jdbc.query(FileSqls.SELECT_JOIN_PRODUCT_IMAGE_BY_PRODUCT_ID, params, fileProductImageRowMapper);
		} catch(DataAccessException e) {
			log.error("FileDao::selectJoinProductImageByProductId",e);
			return null;
		}
    }
    
    public FileDomain selectById(Integer id) {
    		Map<String, Object> params = Collections.singletonMap("id", id);
    		try {
    			return jdbc.queryForObject(FileSqls.SELECT_BY_ID, params, fileRowMapper);
		} catch(DataAccessException e) {
			log.error("FileDao::selectById",e);
			return null;
		}
    }

	public FileDomain insert(FileDomain file) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(file);
		Integer fileId = insertAction.executeAndReturnKey(params).intValue();
		file.setId(fileId);
		return file;
	}
	
}