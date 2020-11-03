package com.milkit.app.enumer;

public enum CouponStatusEnum {

	REGIST("1"), PUBLISH("2"), USE("3"), DISCARD("4");

    private String value;
    

    CouponStatusEnum(String value) {
		this.value = value;
	}
    
	public String getValue() {		
		return value;
	}
}
