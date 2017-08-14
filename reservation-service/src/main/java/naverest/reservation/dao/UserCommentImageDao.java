package naverest.reservation.dao;

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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import naverest.reservation.dto.FileCommentImage;
import naverest.reservation.jdbc.CustomNamedParameterJdbcTempate;
import naverest.reservation.sql.UserCommentImageSqls;

@Repository
public class UserCommentImageDao {
	private CustomNamedParameterJdbcTempate jdbc;
	private final Logger log = LoggerFactory.getLogger(UserCommentImageDao.class);
	private RowMapper<FileCommentImage> fileCommentImageRowMapper = BeanPropertyRowMapper.newInstance(FileCommentImage.class);
	
	@Autowired
	public UserCommentImageDao(DataSource dataSource) {
		this.jdbc = new CustomNamedParameterJdbcTempate(dataSource, UserCommentImageDao.class);
	}
	
	public List<FileCommentImage> selectJoinCommentImageByProductIdAndUserId (Integer productId, List<Integer> userIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("userIds", userIds);
		try {
			return jdbc.query(UserCommentImageSqls.SELECT_JOIN_COMMENT_IMAGE_BY_PRODUCT_ID, params, fileCommentImageRowMapper);
		} catch(DataAccessException e) {
			log.error("{}",e);
			return null;
		}
	}
	
	
	
}
