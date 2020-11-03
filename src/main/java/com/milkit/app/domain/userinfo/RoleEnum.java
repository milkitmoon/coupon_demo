package com.milkit.app.domain.userinfo;

public enum RoleEnum {

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private String value;
    

    RoleEnum(String value) {
		this.value = value;
	}
    
	public String getValue() {		
		return value;
	}
}
