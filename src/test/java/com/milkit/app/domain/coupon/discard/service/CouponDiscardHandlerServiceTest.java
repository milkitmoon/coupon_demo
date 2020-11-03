package com.milkit.app.domain.coupon.discard.service;


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
import com.milkit.app.api.coupon.request.ApprRequest;
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
import com.milkit.app.domain.coupon.approve.discard.service.CouponDiscardHandlerServiceImpl;
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
class CouponDiscardHandlerServiceTest {
	
	@Autowired
    private AutoPublishCouponDelegateServiceImpl autoPublishCouponDelegateService;
	
	@Autowired
    private CouponDiscardHandlerServiceImpl couponDiscardHandlerService;
	
	@Autowired
    private CouponServiceImpl couponService;


	
	@Test
	@DisplayName("쿠폰폐기를 테스트한다.")
	public void discard_test() throws Exception {
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
		
		ApprResponse result = couponDiscardHandlerService.approve(new ApprRequest("milkit", pubCoupon.getCouponNO()));
log.debug(StringUtil.toJsonString(result));
		assertTrue(result.getApprNO() != null);
	}


/**/	
	@Test
	@DisplayName("쿠폰폐기_폐기불가상태 오류를 테스트한다.")
	public void discard_exception1_test() throws Exception {
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
				
		Date currTime = new Date();
		Coupon pubCoupon = autoPublishCouponDelegateService.publish(couponInfo, new PublishRequest("milkit", couponCD));

		Coupon coupon = couponService.getCoupon(pubCoupon.getCouponNO());
		coupon.setDiscardTime(currTime);;
		coupon.setStatus(CouponStatusEnum.USE.getValue());	//임의로 사용상태로 변경
		coupon.setUpdTime(currTime);
		coupon.setUpdUser("milkit");
		
		Coupon updCoupon = couponService.update(coupon);
		
        Exception exception = assertThrows(ServiceException.class, () -> {
    		ApprResponse result = couponDiscardHandlerService.approve(new ApprRequest("milkit", coupon.getCouponNO()));	
        });
        
log.debug(exception.getMessage());
        
        assertTrue( exception.getMessage().contains("등록 혹은 발행 상태의 쿠폰이 아니면 폐기할 수 없음"));
	}


}
