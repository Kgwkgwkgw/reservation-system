package naverest.reservation.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserCommentWrapper {
	private List<UserComment> userCommentList;
	private CommentStats commentStats;
	
	public List<UserComment> getUserCommentList() {
		return userCommentList;
	}
	public void setUserCommentList(List<UserComment> userCommentList) {
		this.userCommentList = userCommentList;
	}
	public CommentStats getCommentStats() {
		return commentStats;
	}
	public void setCommentStats(CommentStats commentStats) {
		this.commentStats = commentStats;
	}
	@Override
	public String toString() {
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
