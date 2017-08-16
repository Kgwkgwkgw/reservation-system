package naverest.reservation.sql;

public class UserCommentImageSqls {
	public final static String SELECT_JOIN_FILE_AND_COMMENT_BY_PRODUCT_ID_AND_USER_ID  
	="SELECT "
	+"f.id, "
	+"r_u_c.id as reservationUserCommentId, "
	+"f.save_file_name, "
	+"f.user_id as userId "
	+"FROM reservation_user_comment_image r_u_c_i "
	+"INNER JOIN reservation_user_comment r_u_c ON r_u_c_i.reservation_user_comment_id = r_u_c.id "
	+"INNER JOIN file f on r_u_c_i.file_id = f.id "
	+"WHERE r_u_c.product_id = :productId and r_u_c.user_id in (:userIds) "
	+"ORDER BY f.id ASC";
}
