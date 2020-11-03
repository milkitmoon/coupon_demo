package com.milkit.app.enumer;

public enum YNEnum {

	YES("Y"), NO("N");

    private String value;
    

    YNEnum(String value) {
		this.value = value;
	}
    
	public String getValue() {		
		return value;
	}
}
