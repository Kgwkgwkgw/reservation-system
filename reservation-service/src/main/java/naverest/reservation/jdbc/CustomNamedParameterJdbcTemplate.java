package naverest.reservation.jdbc;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class CustomNamedParameterJdbcTemplate extends NamedParameterJdbcTemplate{
	private final Logger log;
	
	public CustomNamedParameterJdbcTemplate(DataSource dataSource, Class<?> clazz) {
		super(dataSource);
		log = LoggerFactory.getLogger(clazz);
	}
	
	@Override
	public <T> T queryForObject(String sql, Map<String, ?> paramMap, RowMapper<T>rowMapper)
			throws DataAccessException {
		try {
				return queryForObject(sql, new MapSqlParameterSource(paramMap), rowMapper);
			} catch(DataAccessException e) {
				log.error("{}",e);
				return null;
			}
	}
	
	@Override
	public <T> List<T> query(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper)
			throws DataAccessException {
		try {
			return query(sql, new MapSqlParameterSource(paramMap), rowMapper);
		} catch(DataAccessException e) {
			log.error("{}",e);
			return null;
		}
	}
}
