package naverest.reservation.sql;
public class ProductSqls {
	private final static String onSale = "1";
	private final static String mainImage = "1";
	public final static String SELECT_ALL_PRODUCTMAIN_LIMIT ="SELECT "
			+ "									 p.id, "
			+ "									 p.name,"
			+ "									 p.description, "
			+ "									 f.id as fileId, "
			+ "									 f.save_file_name as saveFileName, "
			+ "									 d_i.place_name"
			+ "									 FROM product p"
			+ "									 LEFT OUTER JOIN display_info d_i  ON p.id = d_i.product_id"
			+ "									 LEFT OUTER JOIN product_image p_i ON p.id = p_i.product_id"
			+ "								     AND p_i.type = "+mainImage
			+ "									 LEFT OUTER JOIN file f on p_i.file_id = f.id"
			+ " 									 WHERE p.sales_flag ="+onSale
			+ "									 ORDER BY p.id DESC LIMIT :offset, :size";
	
	public final static String COUNT_ALL = "SELECT COUNT(*) "
			+ "							   FROM product p"
			+ "							   WHERE p.sales_flag ="+onSale;
	
	public final static String SELECT_PRODUCTMAIN_BY_CATEGORY_LIMIT = "SELECT "
			+ "											  p.id, "
			+ "											  p.name, "
			+ "											  p.description, "
			+ "											  f.id as fileId, "
			+ "											  f.save_file_name as saveFileName, "
			+ "											  d_i.place_name"
			+ "											  FROM product p"
			+ "											  LEFT OUTER JOIN display_info d_i  ON p.id = d_i.product_id"
			+ "											  LEFT OUTER JOIN product_image p_i ON p.id = p_i.product_id"
			+ "										      AND p_i.type = "+mainImage
			+ "											  LEFT OUTER JOIN file f ON p_i.file_id = f.id"
			+ "											  WHERE p.sales_flag = "+onSale+" AND category_id = :categoryId"
			+ "											  ORDER BY p.id DESC LIMIT :offset, :size";

	
	public final static String COUNT_BY_CATEGORY = "SELECT COUNT(*) "
			+ "									   FROM product p"
			+ "									   WHERE p.sales_flag ="+onSale+" AND p.category_id = :categoryId";
	
	public final static String SELECT_PRODUCTDETAIL = "select"
			+ "										  p.id, "
			+ "										  p.name, "
			+ "										  p.description, "
			+ "										  p.event, "
			+ "										  p.sales_flag,"
			+ "										  p.sales_end, "
			+ "										  d_i.homepage, "
			+ "										  d_i.tel, "
			+ "										  d_i.email, "
			+ "										  d_i.place_name, "
			+ "										  d_i.place_lot, "
			+ "										  d_i.place_street, "
			+ "										  p_d.content"
			+ "										  from product p"  
			+ "										  left outer join product_detail p_d on p.id = p_d.product_id"  
			+ "    									  left outer join display_info d_i on p.id = d_i.product_id"
			+ "										  where p.id = :id"
			+ "										  order by p.id";
	
	public final static String SELECT_PRODUCT_RESERVATION = "select "
			+ "												p.name,"
			+ "												d_i.place_name,"
			+ "												d_i.display_start,"
			+ "												d_i.display_end,"
			+ "												d_i.observation_time,"
			+ "												f.id as fileId,"
			+ "												f.save_file_name as saveFileName"
			+ "												from product p"
			+ "												left outer join display_info d_i on p.id = d_i.product_id"
			+ "												left outer join product_image p_i on p.id = p_i.product_id and type ="+mainImage
			+ "												left outer join file f on p_i.file_id = f.id"
			+ "												where p.id = :id"
			+ "												order by p.id";
	}
