package naverest.reservation.dao;

import java.util.ArrayList;
import java.util.HashMap;
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

import naverest.reservation.domain.ReservationUserCommentImage;
import naverest.reservation.dto.FileCommentImage;
import naverest.reservation.jdbc.CustomNamedParameterJdbcTemplate;
import naverest.reservation.sql.UserCommentImageSqls;

@Repository
public class UserCommentImageDao {
	private CustomNamedParameterJdbcTemplate jdbc;
	private RowMapper<FileCommentImage> fileCommentImageRowMapper = BeanPropertyRowMapper.newInstance(FileCommentImage.class);
	private SimpleJdbcInsert insertAction;
	
	@Autowired
	public UserCommentImageDao(DataSource dataSource) {
		this.jdbc = new CustomNamedParameterJdbcTemplate(dataSource, UserCommentImageDao.class);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_user_comment_image")
				.usingGeneratedKeyColumns("id");
	}
	
	public List<FileCommentImage> selectJoinFileAndCommentByProductIdAndUserId (Integer productId, List<Integer> userIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("userIds", userIds);
		return jdbc.query(UserCommentImageSqls.SELECT_JOIN_FILE_AND_COMMENT_BY_PRODUCT_ID_AND_USER_ID, params, fileCommentImageRowMapper);
	}
	
	public void insertBatch(List<ReservationUserCommentImage> reservationUserCommentImageList) {
		List<SqlParameterSource> parameters = new ArrayList<SqlParameterSource>();
		for (ReservationUserCommentImage reservationUserCommentImage : reservationUserCommentImageList) {
			parameters.add(new BeanPropertySqlParameterSource(reservationUserCommentImage));
		}
		insertAction.executeBatch(parameters.toArray(new SqlParameterSource[0]));
	}
	
	
}
