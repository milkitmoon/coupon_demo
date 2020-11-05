package com.milkit.app.domain.coupon.couponnumber.service;

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
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;
import com.milkit.app.enumer.CouponDivEnum;
import com.milkit.app.enumer.ExpiryDivEnum;
import com.milkit.app.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Slf4j
class DatabaseCouponNumberGenerateDelegateServiceTest {

	
	@Autowired
    private DatabaseCouponNumberGenerateDelegateServiceImpl databaseCouponNumberGenerateDelegateService;


	
	@Test
	@DisplayName("DB기반 Coupon number를 생성 테스트한다.")
	public void generateCouponNumber_test() throws Exception {
		String couponCD = "000001";
		
		CouponInfo couponInfo = new CouponInfo();
		couponInfo.setCouponCD(couponCD);
		couponInfo.setCouponNM("테스트쿠폰");
		couponInfo.setCouponDiv(CouponDivEnum.BILL_COUPON.getValue());
		couponInfo.setExpiryDiv(ExpiryDivEnum.EXPIRY_DATE.getValue());
		couponInfo.setExpiryDay(365);
		couponInfo.setFaceAmt(10000l);
		couponInfo.setPubAmt(9000l);
		couponInfo.setDcRate(0.f);
		
		String result = databaseCouponNumberGenerateDelegateService.generateCouponNumber(couponInfo);
log.debug("result:"+result);
		assertTrue(result != null);
	}





}
