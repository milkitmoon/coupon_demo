package com.milkit.app.domain.coupon.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;
import com.milkit.app.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Slf4j
class CouponNOChecksumServiceTest {


	@Autowired
    private CouponNOChecksumServiceImpl couponNOChecksumService;
	
	
	@Test
	@DisplayName("쿠폰번호 checksum을 확인한다.")
	public void getChecksum_test() throws Exception {
		String source = "10000001000000009";
		String result = couponNOChecksumService.getChecksum(source);
log.debug(StringUtil.toJsonString(result));
	}
	
	
	@Test
	@DisplayName("쿠폰번호 checksum이 맞는지 검증한다.")
	public void checksum_test() throws Exception {
		String source = "1000000100000000960";
		boolean result = couponNOChecksumService.checksum(source);
log.debug(StringUtil.toJsonString(result));
	}
	
	
	

}
