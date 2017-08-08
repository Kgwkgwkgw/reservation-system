package naverest.reservation.dto;

import java.util.Collection;
import java.util.Date;
// 상세페이지에서 사용할 dto 
public class ProductDetail {
	//product
	private Integer id;
	private String name;
	private String description;
	private String event;
	private Integer salesFlag;
	private Date salesEnd;
	//display_info
	private String homepage;
	private String tel;
	private String email;
	private String placeName;
	private String placeLot;
	private String placeStreet;
	//product_detail
	private String content;
	
	private Collection<FileImage> fileList;
	private UserCommentWrapper userCommentWrapper;
	
	public Collection<FileImage> getFileList() {
		return fileList;
	}
	public void setFileList(Collection<FileImage> fileList) {
		this.fileList = fileList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPlaceLot() {
		return placeLot;
	}
	public void setPlaceLot(String placeLot) {
		this.placeLot = placeLot;
	}
	public String getPlaceStreet() {
		return placeStreet;
	}
	public void setPlaceStreet(String placeStreet) {
		this.placeStreet = placeStreet;
	}
	public Date getSalesEnd() {
		return salesEnd;
	}
	public void setSalesEnd(Date salesEnd) {
		this.salesEnd = salesEnd;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public UserCommentWrapper getUserCommentWrapper() {
		return userCommentWrapper;
	}
	public void setUserCommentWrapper(UserCommentWrapper userCommentWrapper) {
		this.userCommentWrapper = userCommentWrapper;
	}
	
	public Integer getSalesFlag() {
		return salesFlag;
	}
	public void setSalesFlag(Integer salesFlag) {
		this.salesFlag = salesFlag;
	}
	@Override
	public String toString() {
		return "ProductDetail [id=" + id + ", name=" + name + ", description=" + description + ", event=" + event
				+ ", salesFlag=" + salesFlag + ", sales_end=" + salesEnd + ", homepage=" + homepage + ", tel=" + tel
				+ ", email=" + email + ", placeName=" + placeName + ", placeLot=" + placeLot + ", placeStreet="
				+ placeStreet + ", content=" + content + ", fileList=" + fileList + ", userCommentWrapper="
				+ userCommentWrapper + "]";
	}
}
