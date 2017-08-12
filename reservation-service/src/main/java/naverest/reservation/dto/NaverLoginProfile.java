package naverest.reservation.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NaverLoginProfile implements Serializable{
	private String id;
	private String nickname;
	private String name;
	private String email;
	@JsonProperty("profile_image")
	private String profileImage;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	@Override
	public String toString() {
		return "NaverLoginProfile [id=" + id + ", nickname=" + nickname + ", name=" + name + ", email=" + email
				+ ", profileImage=" + profileImage + "]";
	}
	
	
}
