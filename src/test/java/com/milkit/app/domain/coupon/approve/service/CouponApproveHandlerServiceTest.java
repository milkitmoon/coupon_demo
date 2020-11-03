package com.milkit.app.domain.coupon.approve.service;


import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkit.app.api.coupon.request.PublishRequest;
import com.milkit.app.api.coupon.request.UseApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.api.coupon.response.BillUseApprResponse;
import com.milkit.app.api.coupon.response.DcUseApprResponse;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.approve.use.service.CouponApproveHandlerServiceImpl;
import com.milkit.app.domain.coupon.couponnumber.service.CouponNumberGenerateDelegateService;
import com.milkit.app.domain.coupon.couponnumber.service.DatabaseCouponNumberGenerateDelegateServiceImpl;
import com.milkit.app.domain.coupon.publish.service.AutoPublishCouponDelegateServiceImpl;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;
import com.milkit.app.enumer.CouponDivEnum;
import com.milkit.app.enumer.CouponStatusEnum;
import com.milkit.app.enumer.ExpiryDivEnum;
import com.milkit.app.enumer.YNEnum;
import com.milkit.app.util.DateUtil;
import com.milkit.app.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Slf4j
class CouponApproveHandlerServiceTest {
	
	@Autowired
    private AutoPublishCouponDelegateServiceImpl autoPublishCouponDelegateService;
	
	@Autowired
    private CouponApproveHandlerServiceImpl couponApproveManagerServiceImpl;


	
	@Test
	@DisplayName("금액권 쿠폰승인을 테스트한다.")
	public void bill_approve_test() throws Exception {
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
				
		Coupon pubCoupon = autoPublishCouponDelegateService.publish(couponInfo, new PublishRequest("milkit", couponCD));
		
		Long reqUseAmt = 9000l;
		ApprResponse result = couponApproveManagerServiceImpl.approve(new UseApprRequest("milkit", pubCoupon.getCouponNO(), reqUseAmt));
log.debug(StringUtil.toJsonString(result));
		assertTrue(result.getApprNO() != null);
	}

	@Test
	@DisplayName("금액권 쿠폰승인 거스름돈지급금액비율 테스트한다.")
	public void bill_approve_test2() throws Exception {
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
				
		Coupon pubCoupon = autoPublishCouponDelegateService.publish(couponInfo, new PublishRequest("milkit", couponCD));
		
		Long reqUseAmt = 5000l;
		BillUseApprResponse result = (BillUseApprResponse)couponApproveManagerServiceImpl.approve(new UseApprRequest("milkit", pubCoupon.getCouponNO(), reqUseAmt));
log.debug(StringUtil.toJsonString(result));
		assertTrue(result.getChangeAmt() == 4000l);
	}
	
	@Test
	@DisplayName("금액권 쿠폰승인_유효기간오류를 테스트한다.")
	public void bill_approve_exception1_test() throws Exception {
		String couponCD = "000001";
		
		CouponInfo couponInfo = new CouponInfo();
		couponInfo.setCouponCD(couponCD);
		couponInfo.setCouponNM("테스트쿠폰");
		couponInfo.setCouponDiv(CouponDivEnum.BILL_COUPON.getValue());
		couponInfo.setExpiryDiv(ExpiryDivEnum.FIXED_DATE.getValue());
		couponInfo.setApprStartDT("20200101");
		couponInfo.setApprEndDT("20201001");
		couponInfo.setFaceAmt(10000l);
		couponInfo.setPubAmt(9000l);
		couponInfo.setDcRate(0.f);
				
		Coupon pubCoupon = autoPublishCouponDelegateService.publish(couponInfo, new PublishRequest("milkit", couponCD));
		
        Exception exception = assertThrows(ServiceException.class, () -> {
        	Long reqUseAmt = 9000l;
        	ApprResponse result = couponApproveManagerServiceImpl.approve(new UseApprRequest("milkit", pubCoupon.getCouponNO(), reqUseAmt));	
        });
        
log.debug(exception.getMessage());
        
        assertTrue( exception.getMessage().contains("쿠폰 승인유효일자가 아닙니다"));
	}
	
	@Test
	@DisplayName("할인권 쿠폰승인을 테스트한다.")
	public void dc_coupon_approve_test() throws Exception {
		String couponCD = "000002";
		
		CouponInfo couponInfo = new CouponInfo();
		couponInfo.setCouponCD(couponCD);
		couponInfo.setCouponNM("테스트쿠폰");
		couponInfo.setCouponDiv(CouponDivEnum.DC_COUPON.getValue());
		couponInfo.setExpiryDiv(ExpiryDivEnum.EXPIRY_DATE.getValue());
		couponInfo.setExpiryDay(365);
		couponInfo.setDcRate(10.f);
		couponInfo.setDcPossMinAmt(1000l);
		couponInfo.setDcMaxAmt(10000l);
				
		Coupon pubCoupon = autoPublishCouponDelegateService.publish(couponInfo, new PublishRequest("milkit", couponCD));
		
		Long reqUseAmt = 5000l;
		ApprResponse result = couponApproveManagerServiceImpl.approve(new UseApprRequest("milkit", pubCoupon.getCouponNO(), reqUseAmt));
log.debug(StringUtil.toJsonString(result));
		assertTrue(result.getApprNO() != null);
	}
	
	@Test
	@DisplayName("할인권 쿠폰승인 최대금액조정 테스트한다.")
	public void dc_coupon_approve_test2() throws Exception {
		String couponCD = "000002";
		
		CouponInfo couponInfo = new CouponInfo();
		couponInfo.setCouponCD(couponCD);
		couponInfo.setCouponNM("테스트쿠폰");
		couponInfo.setCouponDiv(CouponDivEnum.DC_COUPON.getValue());
		couponInfo.setExpiryDiv(ExpiryDivEnum.EXPIRY_DATE.getValue());
		couponInfo.setExpiryDay(365);
		couponInfo.setDcRate(30.f);
				
		Coupon pubCoupon = autoPublishCouponDelegateService.publish(couponInfo, new PublishRequest("milkit", couponCD));
		
		Long reqUseAmt = 50000l;
		DcUseApprResponse result = (DcUseApprResponse)couponApproveManagerServiceImpl.approve(new UseApprRequest("milkit", pubCoupon.getCouponNO(), reqUseAmt));
log.debug(StringUtil.toJsonString(result));
		assertTrue(result.getDcAmt() == 10000l);
	}
	
	
	@Test
	@DisplayName("할인권 최소사용금액 오류를 테스트한다.")
	public void dc_coupon_exception1_test() throws Exception {
		String couponCD = "000002";
		
		CouponInfo couponInfo = new CouponInfo();
		couponInfo.setCouponCD(couponCD);
		couponInfo.setCouponNM("테스트쿠폰");
		couponInfo.setCouponDiv(CouponDivEnum.DC_COUPON.getValue());
		couponInfo.setExpiryDiv(ExpiryDivEnum.EXPIRY_DATE.getValue());
		couponInfo.setExpiryDay(365);
		couponInfo.setDcRate(30.f);
				
		Coupon pubCoupon = autoPublishCouponDelegateService.publish(couponInfo, new PublishRequest("milkit", couponCD));
		
        Exception exception = assertThrows(ServiceException.class, () -> {
        	Long reqUseAmt = 500l;
        	DcUseApprResponse result = (DcUseApprResponse)couponApproveManagerServiceImpl.approve(new UseApprRequest("milkit", pubCoupon.getCouponNO(), reqUseAmt));	
        });
        
log.debug(exception.getMessage());
        
        assertTrue( exception.getMessage().contains("할인쿠폰 사용이 가능한 최소사용금액에 미치지 못합니다"));
	}


}
