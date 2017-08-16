package naverest.reservation.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import naverest.reservation.domain.FileDomain;
import naverest.reservation.jdbc.CustomNamedParameterJdbcTemplate;
import naverest.reservation.sql.FileSqls;

@Repository
public class FileDao {
	private CustomNamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;

    private RowMapper<FileDomain> fileRowMapper = BeanPropertyRowMapper.newInstance(FileDomain.class);

    @Autowired
    public FileDao(DataSource dataSource) {
    		this.jdbc = new CustomNamedParameterJdbcTemplate(dataSource, FileDao.class);
    		this.insertAction = new SimpleJdbcInsert(dataSource)
    				.withTableName("file")
    				.usingGeneratedKeyColumns("id");

    }
    
    public FileDomain selectById(Integer id) {
    		Map<String, Object> params = Collections.singletonMap("id", id);
    		return jdbc.queryForObject(FileSqls.SELECT_BY_ID, params, fileRowMapper);
    }

	public FileDomain insert(FileDomain file) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(file);
		Integer fileId = insertAction.executeAndReturnKey(params).intValue();
		file.setId(fileId);
		return file;
	}
	public Integer deleteById(Integer id) {
		Map<String, Object> params = Collections.singletonMap("id", id);
		return jdbc.update(FileSqls.DELETE_BY_ID, params);
	}
	
	public Integer updateByIds(List<Integer> ids) {
		Map<String, Object> params =  Collections.singletonMap("ids", ids);
		return jdbc.update(FileSqls.UPDATE_BY_IDS, params);
		
	}
	
	public void insertBatch(List<FileDomain> fileDomainList) {
		List<SqlParameterSource> parameters = new ArrayList<SqlParameterSource>();
		for (FileDomain fileDomain : fileDomainList) {
			parameters.add(new BeanPropertySqlParameterSource(fileDomain));
		}
		insertAction.executeBatch(parameters.toArray(new SqlParameterSource[0]));
	}	
}
