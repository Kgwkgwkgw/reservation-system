package naverest.reservation.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FileCommentImage {
	private Integer id;
	private Integer reservationUserCommentId;
	private String saveFileName;
	private Integer userId;

	//reservation_user_comment_image
	
	public FileCommentImage(){}
	
	
	public FileCommentImage(Integer id, Integer reservationUserCommentId, String saveFileName, Integer userId) {
		super();
		this.id = id;
		this.reservationUserCommentId = reservationUserCommentId;
		this.saveFileName = saveFileName;
		this.userId = userId;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public Integer getReservationUserCommentId() {
		return reservationUserCommentId;
	}

	public void setReservationUserCommentId(Integer reservationUserCommentId) {
		this.reservationUserCommentId = reservationUserCommentId;
	}

	@Override
	public String toString() {
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
