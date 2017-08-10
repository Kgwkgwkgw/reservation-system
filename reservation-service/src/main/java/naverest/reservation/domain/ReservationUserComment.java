package naverest.reservation.domain;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class ReservationUserComment {
	private Integer id;
	private Integer productId;
	private Integer userId;
	@Range(min = 0, max = 5)
	private Double score;
	@NotEmpty
	@Length(min = 5, max = 400)
	private String comment;
	private Date createDate;
	private Date modifyDate;

	public ReservationUserComment() {
	}

	public ReservationUserComment(Integer productId, Integer userId, Double score, String comment) {
		super();
		this.productId = productId;
		this.userId = userId;
		this.score = score;
		this.comment = comment;
		this.createDate = new Date();
		this.modifyDate = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
}
