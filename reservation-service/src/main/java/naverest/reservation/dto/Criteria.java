package naverest.reservation.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Criteria {
	@Digits(integer=6,fraction=0)
	@Min(value=0)
	@Max(value=999999) 
	private Integer offset;
	@Digits(integer=6,fraction=0)
	@Min(value=1)
	@Max(value=100)
	private Integer size;
	public Criteria() {
		this.offset = 0;
		this.size = 10;
	}
	
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
	
}
