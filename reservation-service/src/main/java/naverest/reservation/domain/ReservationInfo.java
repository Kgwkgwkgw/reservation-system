package naverest.reservation.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import naverest.reservation.annotation.NotZeroMultiple;

@NotZeroMultiple(value = {"generalTicketCount", "youthTicketCount", "childTicketCount"}, message= "티켓 한개는 예매해주세요.")
public class ReservationInfo {
	private Integer id;
	@NotNull(message="상품정보가 필요합니다.")
	private Integer productId;
	private Integer userId;
	private Integer generalTicketCount;
	private Integer youthTicketCount;
	private Integer childTicketCount;
	@NotNull
	private String reservationName;
	@NotEmpty(message="연락처를 입력해주세요.")
	@Size(min=12, max=15)
	private String reservationTel;
	@Email(message="이메일 형식이 올바르지 않습니다.")
	private String reservationEmail;
	private String reservationType;
	private Date reservationDate;
	private Date createDate;
	private Date modifyDate;
	
	@NotNull(message="동의 후에 다시 시도해주세요.")
	private String agree;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGeneralTicketCount() {
		return generalTicketCount;
	}
	public void setGeneralTicketCount(Integer generalTicketCount) {
		this.generalTicketCount = generalTicketCount;
	}
	public Integer getYouthTicketCount() {
		return youthTicketCount;
	}
	public void setYouthTicketCount(Integer youthTicketCount) {
		this.youthTicketCount = youthTicketCount;
	}
	public Integer getChildTicketCount() {
		return childTicketCount;
	}
	public void setChildTicketCount(Integer childTicketCount) {
		this.childTicketCount = childTicketCount;
	}
	public String getReservationName() {
		return reservationName;
	}
	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}
	public String getReservationTel() {
		return reservationTel;
	}
	public void setReservationTel(String reservationTel) {
		this.reservationTel = reservationTel;
	}
	public String getReservationEmail() {
		return reservationEmail;
	}
	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}
	public String getReservationType() {
		return reservationType;
	}
	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getAgree() {
		return agree;
	}
	public void setAgree(String agree) {
		this.agree = agree;
	}
	@Override
	public String toString() {
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
