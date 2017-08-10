package naverest.reservation.sql;

public class FileSqls {
	public final static String SELECT_JOIN_PRODUCT_IMAGE_BY_PRODUCT_ID = "SELECT "
			+ "															f.id, "
			+ "															f.save_file_name as saveFileName, "
			+ "															f.content_type, "
			+ "															f.delete_flag, "
			+ "															p_i.type"
			+"													  		FROM file f"
			+"												  			INNER JOIN product_image p_i ON f.id = p_i.file_id"
			+"													  		WHERE p_i.product_id = :productId"
			+"		    												ORDER BY f.id ASC";
	
	public final static String SELECT_JOIN_COMMENT_IMAGE_BY_PRODUCT_ID = "SELECT "
			+ "															f.id,"
			+ "															r_u_c.id as reservationUserCommentId, "
			+ "															f.save_file_name, "
			+ "														 	f.user_id as userId"
			+ "														  	FROM reservation_user_comment r_u_c"
			+ "													  		INNER JOIN reservation_user_comment_image r_u_c_i ON r_u_c.id = r_u_c_i.reservation_user_comment_id"
			+ "													  		INNER JOIN file f on r_u_c_i.file_id = f.id"
			+ "													  		WHERE r_u_c.product_id = :productId and r_u_c.user_id in (:userIds)"
			+ "													  		ORDER BY f.id ASC";
	
	public final static String UPDATE_BY_IDS = "UPDATE "
			+ "								    file"
			+ "									SET delete_flag = 0"
			+ "									WHERE id IN (:ids)";
	public final static String SELECT_BY_ID = "select "
			+ "								  f.id, "
			+ "								  f.file_name, "
			+ "								  f.save_file_name, "
			+ "								  f.file_length, "
			+ "								  f.content_type"
			+ "								  from file f"
			+ "								  where f.id = :id";
	
	public final static String DELETE_BY_ID = "DELETE "
			+ "								  FROM file "
			+ "								  WHERE id = :id";
	
}
