package kgw.reservation.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kgw.reservation.domain.ReservationUserCommentImage;

@Repository
public class UserCommentImageDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	private RowMapper<ReservationUserCommentImage> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserCommentImage.class);

	@Autowired
	public UserCommentImageDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("RESERVATION_USER_COMMENT_IMAGE")
				.usingGeneratedKeyColumns("id");
	}
	public Integer Insert(ReservationUserCommentImage image) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(image);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	
	
	
}
