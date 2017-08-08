package naverest.reservation.dto;

// File과 productImage 조인, reservation_user_comment_image 조인
public class FileImage {
	private Integer id;
//	private Integer userId;
//	private String fileName;
	private String saveFileName;
//	private Integer fileLength;
	private String contentType;
	private Integer deleteFlag;
//	private Date createDate;
//	private Date modifyDate;
	
	//product_image
	private Integer type;
	
	//reservation_user_comment_image
	private Integer reservationUserCommentId;

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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getReservationUserCommentId() {
		return reservationUserCommentId;
	}

	public void setReservationUserCommentId(Integer reservationUserCommentId) {
		this.reservationUserCommentId = reservationUserCommentId;
	}

	@Override
	public String toString() {
		return "FileImage [id=" + id + ", saveFileName=" + saveFileName + ", contentType=" + contentType
				+ ", deleteFlag=" + deleteFlag + ", type=" + type + ", reservationUserCommentId="
				+ reservationUserCommentId + "]";
	}
	
}
