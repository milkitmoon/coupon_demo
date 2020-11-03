package com.milkit.app.domain.userinfo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Slf4j
class UserInfoServiceTest {

	@Autowired
    private UserInfoServiceImpl userInfoServie;
	
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


	@Test
	public void ID별조회_테스트() throws Exception {
		UserInfo userInfo = userInfoServie.select(1l);

		assertTrue(userInfo != null);
	}
	
	@Test
	public void UserID별조회_테스트() throws Exception {
		UserInfo userInfo = userInfoServie.select("test");

		assertTrue(userInfo != null);
	}

	@Test
	public void 전체조회_테스트() throws Exception {
		List<UserInfo> list = userInfoServie.selectAll();
		
		assertTrue(list.size() > 0);
	}

	@Test
	public void 등록_테스트() throws Exception {
		UserInfo userInfo = new UserInfo("test2", "test2");
		Long id = userInfoServie.insert(userInfo);
		
		assertTrue(id > 1L);
	}
	
	@Test
	public void 토큰refresh_테스트() throws Exception {
		UserInfo userInfo = userInfoServie.select("test");
		String token = jwtTokenProvider.createRefreshToken(userInfo);
		
		JwtToken jwtToken = userInfoServie.refresh(token);
		
		assertTrue(jwtToken.getRefreshToken() != null);
	}

}
