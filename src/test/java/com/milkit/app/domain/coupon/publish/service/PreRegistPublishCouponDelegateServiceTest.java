package com.milkit.app.domain.coupon.publish.service;

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
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;
import com.milkit.app.enumer.CouponDivEnum;
import com.milkit.app.enumer.CouponStatusEnum;
import com.milkit.app.enumer.ExpiryDivEnum;
import com.milkit.app.enumer.YNEnum;
import com.milkit.app.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Slf4j
class PreRegistPublishCouponDelegateServiceTest {

	
	@Autowired
    private PreRegistPublishCouponDelegateServiceImpl preRegistPublishCouponDelegateService;

	@Autowired
    private CouponServiceImpl couponService;

	

	
	@Test
	@DisplayName("사전등록된 쿠폰발행을 테스트한다.")
	public void publish_test() throws Exception {
		
		String couponCD = "000001";
		
		Coupon preRegistCoupon1 = Coupon
				.builder()
				.couponCD(couponCD)
				.couponNO("12345")
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
		Coupon preRegistCoupon2 = Coupon
				.builder()
				.couponCD(couponCD)
				.couponNO("12346")
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
		couponService.insert(preRegistCoupon1);
		couponService.insert(preRegistCoupon2);

		
		CouponInfo couponInfo = new CouponInfo();
		couponInfo.setCouponCD(couponCD);
		couponInfo.setCouponNM("테스트쿠폰");
		couponInfo.setCouponDiv(CouponDivEnum.BILL_COUPON.getValue());
		couponInfo.setExpiryDiv(ExpiryDivEnum.EXPIRY_DATE.getValue());
		couponInfo.setExpiryDay(365);
		couponInfo.setFaceAmt(10000l);
		couponInfo.setPubAmt(9000l);
		couponInfo.setDcRate(0.f);
		
				
		Coupon result = preRegistPublishCouponDelegateService.publish(couponInfo, new PublishRequest("milkit", couponCD));
log.debug(StringUtil.toJsonString(result));

		assertTrue( result.getCouponNO().equals("12346") );
	}
	

/*
	@Test
	@DisplayName("사전등록된 쿠폰 정보가 없을 경우 예외를 테스트한다.")
    public void publishException_TEST() {
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
		
        Exception exception = assertThrows(ServiceException.class, () -> {
        	preRegistPublishCouponDelegateService.publish(couponInfo, new PublishRequest("milkit", couponCD));
        });
log.debug(exception.getMessage());
        assertTrue( exception.getMessage().contains("발행할 수 있는 쿠폰정보가 없습니다") );
    }
*/

}
