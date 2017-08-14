package naverest.reservation.domain;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

public class Category {
	private Integer id;
	@NotNull
	@NotBlank(message="내용을 입력 해주세요.")
	private String name;
	
	public Category () {
	}
	
	public Category(String name) {
		this.name = name;
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
	@Override
	public String toString() {
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}