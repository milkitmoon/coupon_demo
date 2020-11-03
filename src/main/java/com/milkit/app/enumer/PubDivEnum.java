package com.milkit.app.enumer;

public enum PubDivEnum {

	AUTO_PUBLISH("1"), PRE_REGIST("2");

    private String value;
    

    PubDivEnum(String value) {
		this.value = value;
	}
    
	public String getValue() {		
		return value;
	}
}
