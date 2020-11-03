package com.milkit.app.domain.coupon.publish.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkit.app.api.coupon.request.PublishRequest;
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;
import com.milkit.app.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Slf4j
class CouponPublishHandlerServiceTest {

	
	@Autowired
    private CouponPublishHandlerServiceImpl couponPublishManagerService;


	
	@Test
	@DisplayName("쿠폰발행을 테스트한다.")
	public void publish_test() throws Exception {
				
		Coupon pubCoupon = couponPublishManagerService.approve(new PublishRequest("milkit", "000001"));
log.debug(StringUtil.toJsonString(pubCoupon));
		assertTrue(pubCoupon.getCouponNO() != null);
	}


}
