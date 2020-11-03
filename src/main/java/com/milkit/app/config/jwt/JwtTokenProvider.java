package com.milkit.app.config.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkit.app.common.AppCommon;
import com.milkit.app.common.response.GenericResponse;
import com.milkit.app.config.jwt.filter.JwtAuthenticationProvider;
import com.milkit.app.domain.userinfo.UserInfo;

@Slf4j
public class JwtTokenProvider {

	
    public JwtTokenProvider() {}

    
	public String createAccessToken(JwtDetails principal) {
		return createToken(principal, AppCommon.getInstance().JWT_EXPIRATION_TIME);
	}
	
	public String createRefreshToken(JwtDetails principal) {
        return createToken(principal, AppCommon.getInstance().JWT_REFRESH_EXPIRATION_TIME);
	}
	
	private String createToken(JwtDetails principal, long expirationTime) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTime);
        
		return Jwts.builder().setSubject(principal.getSubject())
				  .setClaims(principal.getClaims())
		          .setIssuedAt(now)
		          .setExpiration(validity)
		          .signWith(SignatureAlgorithm.HS256, AppCommon.getInstance().JWT_SECRETKEY)
		          .compact();
	}
	

	public Map<String, String> createHeader(JwtDetails principal) {
    	String token = createAccessToken(principal);
    	String refreshToken = createRefreshToken(principal);
    	
        Map<String, String> header = new HashMap<String, String>();
        header.put(AppCommon.getInstance().JWT_HEADER_STRING, token);
        header.put(AppCommon.getInstance().JWT_REFRESH_HEADER_STRING, refreshToken);

        return header;
    }

	public JwtToken createBody(JwtDetails jwtDetails) throws JsonProcessingException {
    	String token = createAccessToken(jwtDetails);
    	String refreshToken = createRefreshToken(jwtDetails);
    	
    	return new JwtToken(token, refreshToken);
    }
    
    public Claims getClaims(String token) {
        try {
            Claims claims = parseClaims(token);

            log.debug("expireTime :" + claims.getExpiration());
            log.debug("userID :" + claims.get("name"));
            log.debug("userNM :" + claims.get("userNM"));
            log.debug("authRole :" + claims.get("authRole"));
            
            return claims;

        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return null;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return null;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return null;
        }
    }

	public String getUsername(String token) throws JwtException {
		Claims claims = parseClaims(token);
	
		return (String) claims.get("name");
	}
	

    private Claims parseClaims(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(AppCommon.getInstance().JWT_SECRETKEY))
                .parseClaimsJws(token).getBody();
    }
    
}
