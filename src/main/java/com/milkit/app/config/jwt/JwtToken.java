package com.milkit.app.config.jwt;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;

@ApiModel
@RequiredArgsConstructor
public class JwtToken {
	
	public final static String TOKEN_TYPE = "bearer";
	
	
	@ApiModelProperty(value="JWT AccessToken 정보")
	private String accessToken;
	@ApiModelProperty(value="JWT RefreshToken 정보")
	private String refreshToken;
	@ApiModelProperty(value="JWT TokenType 정보")
	private String tokenType;
	

	public JwtToken(String accessToken, String refreshToken) {
		this(accessToken, refreshToken, TOKEN_TYPE);
	}
	
	public JwtToken(String accessToken, String refreshToken, String tokenType) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.tokenType = tokenType;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override  
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.SHORT_PREFIX_STYLE
		);
	}
}
