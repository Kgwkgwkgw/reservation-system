package naverest.reservation.sql;

public class ProductPriceSqls {
	public final static String SELECT_BY_PRODUCT_ID ="SELECT"
+ "												     price_type,"
+ "												     price,"
+ "												     discount_rate"
+ "	   											     FROM product_price"
+ "											   		 WHERE product_id = :productId"
+ "	   											     ORDER BY id";
}
