package com.milkit.app.domain.coupon.couponnumber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

import com.milkit.app.common.CouponSizeCommon;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.service.CouponNOChecksumServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponnoseq.service.CouponNoSeqServiceImpl;
import com.milkit.app.util.Base62Util;
import com.milkit.app.util.StringUtil;

@Slf4j
@Component
public class DatabaseCouponNumberGenerateDelegate2ServiceImpl implements CouponNumberGenerateDelegateService {
	
	@Autowired
    private CouponNoSeqServiceImpl couponNoSeqService;
	
	@Autowired
    private CouponNOChecksumServiceImpl couponNOChecksumService;

	@Override
	public String generateCouponNumber(CouponInfo couponInfo) throws Exception {
		String couponNO = null;
		
		String couponCD = couponInfo.getCouponCD();
		long currTime = System.currentTimeMillis();
		long couponnoSeq = couponNoSeqService.selectNextCouponnoSeq(couponCD) % 1000000;
		String couponSeqStr = StringUtil.leftPad(String.valueOf(couponnoSeq), "0", 6);
		
		StringBuilder builder = new StringBuilder();
		builder
		.append(couponCD)
		.append(currTime)
		.append(couponSeqStr)
		;

		String numericNO = builder.toString();
//log.debug("numericNO:"+numericNO);
		couponNO = convertAlphanumericNO(numericNO);
		
		return couponNO;
	}

	private String convertAlphanumericNO(String numericNO) {
		String couponNO = Base62Util.encode(numericNO);

		int randomSize = CouponSizeCommon.COUPON_ALNU_NO_SIZE - couponNO.length();

		if(randomSize > 0) {
			String randomStr = RandomStringUtils.random(randomSize, Base62Util.BASE62_STR);
			couponNO = couponNO+randomStr;
		}

		return couponNO;
	}



}
