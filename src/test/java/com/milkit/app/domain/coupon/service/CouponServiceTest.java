package com.milkit.app.domain.coupon.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
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
import com.milkit.app.enumer.CouponStatusEnum;
import com.milkit.app.enumer.YNEnum;
import com.milkit.app.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Slf4j
class CouponServiceTest {


	@Autowired
    private CouponServiceImpl couponService;
	
	@Test
	@DisplayName("쿠폰을 등록한다.")
	public void getCoupon_test() throws Exception {
		String couponCD = "000001";
		String couponNO = "92345";
		
		Coupon coupon = Coupon
				.builder()
				.couponCD(couponCD)
				.couponNO(couponNO)
				.userID("milkit")
				.registDT("20201014")
				.pubDT("20201014")
				.apprStartDT("20201014")
				.apprEndDT("20211014")
				.pubTime(new Date())
				.faceAmt(10000l)
				.pubAmt(9000l)
				.dcRate(0.f)
				.status(CouponStatusEnum.PUBLISH.getValue())
				.useYN(YNEnum.YES.getValue())
				.instTime(new Date())
				.updTime(new Date())
				.instUser("milkit")
				.updUser("milkit")
				.build();
		
		Coupon coupon2 = Coupon
				.builder()
				.couponCD(couponCD)
				.couponNO("92346")
				.userID("milkit")
				.registDT("20201014")
				.pubDT("20201014")
				.apprStartDT("20201014")
				.apprEndDT("20211014")
				.pubTime(new Date())
				.faceAmt(10000l)
				.pubAmt(9000l)
				.dcRate(0.f)
				.status(CouponStatusEnum.PUBLISH.getValue())
				.useYN(YNEnum.YES.getValue())
				.instTime(new Date())
				.updTime(new Date())
				.instUser("milkit")
				.updUser("milkit")
				.build();
		
		couponService.insert(coupon);
		couponService.insert(coupon2);
		
		Coupon result = couponService.getCoupon(couponNO);

log.debug("result:"+result);
		
		assertTrue(result.getCouponNO().equals(couponNO));
	}
	
	@Test
	@DisplayName("쿠폰을 등록한다.")
	public void insert_test() throws Exception {
		String couponCD = "000001";
		
		Coupon coupon = Coupon
				.builder()
				.couponCD(couponCD)
				.couponNO("12347")
				.userID("milkit")
				.registDT("20201014")
				.pubDT("20201014")
				.apprStartDT("20201014")
				.apprEndDT("20211014")
				.pubTime(new Date())
				.faceAmt(10000l)
				.pubAmt(9000l)
				.dcRate(0.f)
				.status(CouponStatusEnum.PUBLISH.getValue())
				.useYN(YNEnum.YES.getValue())
				.instTime(new Date())
				.updTime(new Date())
				.instUser("milkit")
				.updUser("milkit")
				.build();
		
		Integer result = couponService.insert(coupon);

log.debug("result:"+result);
		
		assertTrue(result == 1);
	}
	
	
	@Test
	@DisplayName("사전등록된 쿠폰을 조회한다.")
	public void getPreRegistCoupon_test() throws Exception {
		String couponCD = "000001";
		
		Coupon coupon = Coupon
				.builder()
				.couponCD(couponCD)
				.couponNO("12350")
				.userID("milkit")
				.registDT("20201014")
				.pubDT("20201014")
				.apprStartDT("20201014")
				.apprEndDT("20211014")
				.pubTime(new Date())
				.faceAmt(10000l)
				.pubAmt(9000l)
				.dcRate(0.f)
				.status(CouponStatusEnum.REGIST.getValue())
				.useYN(YNEnum.YES.getValue())
				.instTime(new Date())
				.updTime(new Date())
				.instUser("milkit")
				.updUser("milkit")
				.build();
		Coupon coupon2 = Coupon
				.builder()
				.couponCD(couponCD)
				.couponNO("12351")
				.userID("milkit")
				.registDT("20201014")
				.pubDT("20201014")
				.apprStartDT("20201014")
				.apprEndDT("20211014")
				.pubTime(new Date())
				.faceAmt(10000l)
				.pubAmt(9000l)
				.dcRate(0.f)
				.status(CouponStatusEnum.REGIST.getValue())
				.useYN(YNEnum.YES.getValue())
				.instTime(new Date())
				.updTime(new Date())
				.instUser("milkit")
				.updUser("milkit")
				.build();
		couponService.insert(coupon);
		couponService.insert(coupon2);
		
		Coupon result = couponService.getPreRegistCoupon(couponCD);
		
log.debug("result:"+result);
		
		assertTrue(result.getCouponNO().equals("12350") || result.getCouponNO().equals("12351"));
	}
	
	@Test
	@DisplayName("특정 쿠폰정보에 대하여 등록된 쿠폰갯수를 조회한다.")
	public void getPublishedCouponQty_test() throws Exception {
		String couponCD = "000001";
		
		Coupon coupon = Coupon
				.builder()
				.couponCD(couponCD)
				.couponNO("12348")
				.userID("milkit")
				.registDT("20201014")
				.pubDT("20201014")
				.apprStartDT("20201014")
				.apprEndDT("20211014")
				.pubTime(new Date())
				.faceAmt(10000l)
				.pubAmt(9000l)
				.dcRate(0.f)
				.status(CouponStatusEnum.PUBLISH.getValue())
				.useYN(YNEnum.YES.getValue())
				.instTime(new Date())
				.updTime(new Date())
				.instUser("milkit")
				.updUser("milkit")
				.build();
		Coupon coupon2 = Coupon
				.builder()
				.couponCD(couponCD)
				.couponNO("12349")
				.userID("milkit")
				.registDT("20201014")
				.pubDT("20201014")
				.apprStartDT("20201014")
				.apprEndDT("20211014")
				.pubTime(new Date())
				.faceAmt(10000l)
				.pubAmt(9000l)
				.dcRate(0.f)
				.status(CouponStatusEnum.PUBLISH.getValue())
				.useYN(YNEnum.YES.getValue())
				.instTime(new Date())
				.updTime(new Date())
				.instUser("milkit")
				.updUser("milkit")
				.build();
		couponService.insert(coupon);
		couponService.insert(coupon2);
		
		int result = couponService.getPublishedCouponQty(couponCD);
		
log.debug("result:"+result);
		
		assertTrue(result >= 2);
	}

}
