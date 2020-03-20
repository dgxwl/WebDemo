package cn.abc.def.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.abc.def.domain.Date2MillisSerializer;

public class JsonCusUser {

	private String name;
	/*
	 * 在field上添加注解, using指定为定制的转换类.class
	 */
	@JsonSerialize(using = Date2MillisSerializer.class)
	private Date signUpDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}

}
