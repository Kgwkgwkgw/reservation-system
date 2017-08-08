package naverest.reservation.dto;

public class CommentStats {
	// 코멘트 개수
	private Integer count;
	// 코멘트 평점의 평균
	private Double averageScore;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(Double averageScore) {
		this.averageScore = averageScore;
	}
	
	
}
