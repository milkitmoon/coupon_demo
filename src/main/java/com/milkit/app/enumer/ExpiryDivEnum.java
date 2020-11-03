package com.milkit.app.enumer;

public enum ExpiryDivEnum {

	FIXED_DATE("1"), EXPIRY_DATE("2");

    private String value;
    

    ExpiryDivEnum(String value) {
		this.value = value;
	}
    
	public String getValue() {		
		return value;
	}
}
