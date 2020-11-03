package com.milkit.app.enumer;

public enum TradeDivEnum {

	PUBLISH("1"), QUERY("2"), USE("3"), DISCARD("4");

    private String value;
    

    TradeDivEnum(String value) {
		this.value = value;
	}
    
	public String getValue() {		
		return value;
	}
}
