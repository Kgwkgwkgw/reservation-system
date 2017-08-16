package naverest.reservation.sql;

public class FileSqls {
	public final static String SELECT_BY_ID = "select "
			+ "								  f.id, "
			+ "								  f.file_name, "
			+ "								  f.save_file_name, "
			+ "								  f.file_length, "
			+ "								  f.content_type"
			+ "								  from file f"
			+ "								  where f.id = :id";
	public final static String DELETE_BY_ID 
	= "DELETE "
	+ "FROM file "
	+ "WHERE id = :id";
	public final static String UPDATE_BY_IDS 
	="UPDATE "
	+"file "
	+"SET delete_flag = 0 "
	+"WHERE id IN (:ids)";
}
