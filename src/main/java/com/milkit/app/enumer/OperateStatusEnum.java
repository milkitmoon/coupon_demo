package com.milkit.app.enumer;

public enum OperateStatusEnum {

	REGIST("1"), CONFIRM("2");

    private String value;
    

    OperateStatusEnum(String value) {
		this.value = value;
	}
    
	public String getValue() {		
		return value;
	}
}
