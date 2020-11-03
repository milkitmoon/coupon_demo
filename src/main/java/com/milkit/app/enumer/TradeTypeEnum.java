package com.milkit.app.enumer;

public enum TradeTypeEnum {

	NORMAL("1"), CANCEL("2");

    private String value;
    

    TradeTypeEnum(String value) {
		this.value = value;
	}
    
	public String getValue() {		
		return value;
	}
}
