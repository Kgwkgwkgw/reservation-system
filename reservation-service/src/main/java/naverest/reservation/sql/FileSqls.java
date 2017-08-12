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
			+"		    													ORDER BY f.id ASC";
	public final static String SELECT_BY_ID = "select "
			+ "								  f.id, "
			+ "								  f.file_name, "
			+ "								  f.save_file_name, "
			+ "								  f.file_length, "
			+ "								  f.content_type"
			+ "								  from file f"
			+ "								  where f.id = :id";
}
