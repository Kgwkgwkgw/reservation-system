package naverest.reservation.sql;

public class ProductImageSqls {
	public final static String SELECT_JOIN_FILE_BY_PRODUCT_ID = "SELECT "
			+ "													f.id, "
			+ "													f.save_file_name as saveFileName, "
			+ "													f.content_type, "
			+ "													f.delete_flag, "
			+ "													p_i.type"
			+"												  	FROM product_image p_i"
			+"													INNER JOIN file f ON p_i.file_id = f.id "
			+"													WHERE p_i.product_id = :productId"
			+"		    											ORDER BY f.id ASC";
}
