package naverest.reservation.sql;

public class CategorySqls {
	public final static String SELECT_BY_ID = "SELECT "
			+ "								  id,"
			+ "								  name "
			+ "								  FROM"
			+ "								  category "
			+ "								  WHERE "
			+ "								  id = :id";
	public final static String SELECT_BY_NAME ="SELECT "
			+ "								   id "
			+ "								   FROM "
			+ "								   category "
			+ "								   WHERE "
			+ "								   name = :name "
			+ "								   ORDER BY id";
    public final static String DELETE_BY_ID = "DELETE "
    		+ "									  FROM "
    		+ "									  category "
    		+ "									  WHERE id = :id";
    public final static String UPDATE_BY_ID = "UPDATE "
    		+ "									  category "
    		+ "									  SET "
    		+ "									  name = :name "
    		+ "									  WHERE id = :id";
    public final static String SELECT_ALL = "SELECT "
    		+ "									id, "
    		+ "									name "
    		+ "									FROM "
    		+ "									category "
    		+ "									ORDER BY id";
}
