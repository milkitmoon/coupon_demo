package com.milkit.app.domain.couponinfo.service;

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
class CouponInfoServiceTest {


	@Autowired
    private CouponInfoServiceImpl couponInfoService;
	
	
	@Test
	@DisplayName("쿠폰코드에 따른 쿠폰기본정보를 조회한다.")
	public void getCouponInfo_test() throws Exception {
		String couponCD = "000001";
		CouponInfo result = couponInfoService.getCouponInfo(couponCD);
log.debug(StringUtil.toJsonString(result));
		assertTrue(result.getCouponCD().equals(couponCD));
	}
	

}
