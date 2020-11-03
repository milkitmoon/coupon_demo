package com.milkit.app.domain.coupon.publish.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.milkit.app.api.coupon.request.PublishRequest;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.couponnumber.service.CouponNumberGenerateDelegateService;
import com.milkit.app.domain.coupon.couponnumber.service.DatabaseCouponNumberGenerateDelegateServiceImpl;
import com.milkit.app.domain.coupon.dao.CouponDao;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.enumer.CouponStatusEnum;
import com.milkit.app.enumer.ExpiryDivEnum;
import com.milkit.app.enumer.YNEnum;
import com.milkit.app.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AutoPublishCouponDelegateServiceImpl implements CouponPublishDelegateService {
	
	@Autowired
	private CouponServiceImpl couponService;

	@Autowired
	private DatabaseCouponNumberGenerateDelegateServiceImpl databaseCouponNumberGenerateDelegateService;

	
	@Override
	public Coupon publish(CouponInfo couponInfo, PublishRequest publishRequest) throws Exception {
		Date pubTime = new Date();
		String pubDT = DateUtil.getTimeString(pubTime, "yyyyMMdd");
		String[] apprDT = getApprDT(pubDT, couponInfo);
		
		String couponNO = generateCouponNO(couponInfo, publishRequest);
		
		Coupon pubCoupon = Coupon
				.builder()
				.couponCD(couponInfo.getCouponCD())
				.couponNO(couponNO)
				.userID(publishRequest.getUserID())
				.registDT(pubDT)
				.pubDT(pubDT)
				.apprStartDT(apprDT[0])
				.apprEndDT(apprDT[1])
				.pubTime(pubTime)
				.faceAmt(couponInfo.getFaceAmt())
				.pubAmt(couponInfo.getPubAmt())
				.dcRate(couponInfo.getDcRate())
				.status(CouponStatusEnum.PUBLISH.getValue())
				.useYN(YNEnum.YES.getValue())
				.instTime(pubTime)
				.updTime(pubTime)
				.instUser(publishRequest.getUserID())
				.updUser(publishRequest.getUserID())
				.couponNM(couponInfo.getCouponNM())
				.couponDiv(couponInfo.getCouponDiv())
				.build();
		
		couponService.insert(pubCoupon);

log.debug(pubCoupon.toString());
		
		return pubCoupon;
	}

	
	private String generateCouponNO(CouponInfo couponInfo, PublishRequest publishRequest) throws Exception {
		CouponNumberGenerateDelegateService couponNumberGenerateDelegateService = databaseCouponNumberGenerateDelegateService;

		return couponNumberGenerateDelegateService.generateCouponNumber(couponInfo);
	}


	private String[] getApprDT(String pubDT, CouponInfo couponInfo) throws ParseException {
		String[] arrrDT = new String[2];
		
		if( couponInfo.getExpiryDiv().equals(ExpiryDivEnum.FIXED_DATE.getValue()) ) {
			arrrDT[0] = couponInfo.getApprStartDT();
			arrrDT[1] = couponInfo.getApprEndDT();
		} else {
			arrrDT[0] = pubDT;
			arrrDT[1] = DateUtil.plusDay("yyyyMMdd", pubDT, couponInfo.getExpiryDay());
		}

		return arrrDT;
	}


}
