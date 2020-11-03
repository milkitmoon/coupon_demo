package com.milkit.app.config.jwt;

import java.util.Map;

public interface JwtDetails {
	
	public String getSubject();
	public Map<String, Object> getClaims();

}
