package naverest.reservation.dto;

import java.math.BigDecimal;

public class CommentStats {
	private Integer count;
	private BigDecimal averageScore;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public BigDecimal getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(BigDecimal averageScore) {
		this.averageScore = averageScore;
	}
}
