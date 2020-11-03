package com.milkit.app.config.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkit.app.common.AppCommon;
import com.milkit.app.common.ErrorCodeEnum;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.common.response.GenericResponse;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.userinfo.UserInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
    private JwtTokenProvider jwtTokenProvider;

    
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	UsernamePasswordAuthenticationToken authenticationToken;
    	
		try {
			UserInfo credentials = new ObjectMapper().readValue(request.getInputStream(), UserInfo.class);
	        authenticationToken = new UsernamePasswordAuthenticationToken(
	                credentials.getUsername(),
	                credentials.getPassword(),
	                new ArrayList<>()
	        );
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException(ErrorCodeEnum.AttemptAuthenticationException.getCode());
		}

        return this.getAuthenticationManager().authenticate(authenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    	UserInfo principal = (UserInfo) authResult.getPrincipal();
    	
//    	Map<String, String> header = jwtTokenProvider.createHeader(principal);
//    	header.forEach((key, value) -> response.addHeader(key, value));
    	
    	writeAuthenticationBody(response, principal);
    }
    
    private void writeAuthenticationBody(HttpServletResponse response, UserInfo principal) throws IOException {
    	JwtToken jwtToken = jwtTokenProvider.createBody(principal);
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	response.getWriter().write( objectMapper.writeValueAsString(new GenericResponse<JwtToken>((jwtToken))) );
    }
}
