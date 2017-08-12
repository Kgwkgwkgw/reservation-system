package naverest.reservation.sql;

public class UserCommentSqls {
	public final static String SELECT_BY_PRODUCT_ID_LIMIT = "SELECT  "
			+ "												r_u_c.id, "
			+ "												r_u_c.score, "
			+ "												r_u_c.comment,"
			+ "												p.name, "
			+ "												r_u_c.modify_date, "
			+ "												r_u_c.create_date, "
			+ "												r_u_c.user_id, "
			+ "												u.username, "
			+ "												r_i.reservation_date"
			+"												FROM reservation_user_comment r_u_c"
			+ "												INNER JOIN users u ON r_u_c.user_id = u.id"
			+ "												INNER JOIN product p ON r_u_c.product_id = p.id"
			+ "												INNER JOIN reservation_info r_i ON r_u_c.user_id  =  r_i.user_id and r_u_c.product_id = r_i.product_id"
			+"												WHERE r_u_c.product_id =:productId"
			+ "												ORDER BY r_u_c.id desc"
			+"												LIMIT :offset, :size";
	
	public final static String SELECT_STATS_BY_PRODUCT_ID = "select "
			+ "												count(*) as count, "
			+ "												avg(score) as averageScore"
			+ "												from reservation_user_comment"
			+ "												where product_id =:productId";
		
	
}
