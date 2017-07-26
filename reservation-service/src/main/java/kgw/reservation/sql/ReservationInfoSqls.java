package kgw.reservation.sql;

public class ReservationInfoSqls {
	public final static Integer ASKING = 1;
	public final static Integer CONFIRM = 2;
	public final static Integer COMPLETION = 3;
	public final static Integer CANCELLATION = 4;
	public final static Integer REFUND = 5;
	
	public final static String SELECT_ALL_RESERVATION_BY_USER_ID = "SELECT "
			+ "													   r_i.id,"
			+ "													   r_i.product_id as productId,"
			+ "													   r_i.user_id as userId,"
			+ "													   r_i.general_ticket_count as generalTicketCount,"
			+ "													   r_i.youth_ticket_count as youthTicketCount,"
			+ "													   r_i.child_ticket_count as childTicketCount,"
			+ "													   r_i.reservation_type as reservationType,"
			+ "													   r_i.reservation_date as reservationDate,"
			+ "													   r_u_c.id as commentId,"
			+ "													   p.name"
			+ "													   FROM reservation_info r_i"
			+ "													   INNER JOIN product p on r_i.product_id = p.id"
			+ "													   LEFT OUTER JOIN reservation_user_comment r_u_c on r_i.user_id = r_u_c.user_id"
			+ "													   WHERE r_i.user_id = :userId";
	public final static String SELECT_RESERVATION_BY_USER_ID_AND_TYPE = "SELECT "
			+ "													   r_i.id,"
			+ "													   r_i.product_id as productId,"
			+ "													   r_i.user_id as userId,"
			+ "													   r_i.general_ticket_count as generalTicketCount,"
			+ "													   r_i.youth_ticket_count as youthTicketCount,"
			+ "													   r_i.child_ticket_count as childTicketCount,"
			+ "													   r_i.reservation_type as reservationType,"
			+ "													   r_i.reservation_date as reservationDate,"
			+ "													   r_u_c.id as commentId,"
			+ "													   p.name"
			+ "													   FROM reservation_info r_i"
			+ "													   INNER JOIN product p on r_i.product_id = p.id"
			+ "													   LEFT OUTER JOIN reservation_user_comment r_u_c on r_i.user_id = r_u_c.user_id"
			+ "													   WHERE r_i.user_id = :userId and r_i.reservation_type =:type";
	public final static String UPDATE_TYPE_BY_ID =   		  "UPDATE"
			+ "											   reservation_info"
			+ "											   SET"
			+ "											   reservation_type = :type"
			+ "											   WHERE"
			+ "											   id = :id";
	
	public final static String DELETE_RESERVATION_BY_TYPE= "DELETE"
			+ "											  FROM reservation_info"
			+ "											  WHERE reservation_type = :type";
	
}
