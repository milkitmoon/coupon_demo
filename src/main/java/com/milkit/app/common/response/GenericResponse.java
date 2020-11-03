package com.milkit.app.common.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.milkit.app.common.ErrorCodeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class GenericResponse<T> implements Serializable {

	@ApiModelProperty(value="결과코드", notes="형식 (0:성공, others:실패)")
	private String code;
	@ApiModelProperty(value="결과메시지")
	private String message;
	@ApiModelProperty(value="결과값", notes="형식 (template으로 정의된 값)")
	private T value;
	
	
	public GenericResponse() {
		this(ErrorCodeEnum.OK.getCode(), "성공했습니다");
	}

	public GenericResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public GenericResponse(T value) {
		this();
		this.value = value;
	}
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	

    public static GenericResponse<?> success() {
        return new GenericResponse();
    }

}
