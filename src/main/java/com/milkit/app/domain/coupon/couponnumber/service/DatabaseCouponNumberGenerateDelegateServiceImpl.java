package com.milkit.app.domain.coupon.couponnumber.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.milkit.app.common.CouponSizeCommon;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.service.CouponNOChecksumServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponnoseq.service.CouponNoSeqServiceImpl;
import com.milkit.app.util.StringUtil;

@Slf4j
@Component
public class DatabaseCouponNumberGenerateDelegateServiceImpl implements CouponNumberGenerateDelegateService {
	
	@Autowired
    private CouponNoSeqServiceImpl couponNoSeqService;
	
	@Autowired
    private CouponNOChecksumServiceImpl couponNOChecksumService;

	@Override
	public String generateCouponNumber(CouponInfo couponInfo) throws Exception {
		String couponNO = null;
		
		String couponCD = couponInfo.getCouponCD();
		long result = couponNoSeqService.selectNextCouponnoSeq(couponCD);
		String couponSeqStr = StringUtil.leftPad(String.valueOf(result), "0", CouponSizeCommon.COUPON_SEQ_SIZE);
		
		StringBuilder builder = new StringBuilder();
		builder
		.append(couponCD)
		.append(couponSeqStr);
		
		String checksum = couponNOChecksumService.getChecksum(builder.toString());
		builder.append(checksum);

		String numericNO = builder.toString();
//log.debug("numericNO:"+numericNO);
		couponNO = convertAlphanumericNO(numericNO);
		
		return couponNO;
	}

	private String convertAlphanumericNO(String numericNO) {
		String alphanumericNO = Long.toString(Long.valueOf(numericNO), 36);

		int randomSize = CouponSizeCommon.COUPON_ALNU_NO_SIZE - alphanumericNO.length();

		if(randomSize > 0) {
/*
			String randomStr = RandomStringUtils.random(randomSize, "abcdefghijklmnopqrstuvwxyz0123456789");
			alphanumericNO = alphanumericNO+randomStr;
*/

			alphanumericNO = StringUtil.leftPad(String.valueOf(alphanumericNO), "0", CouponSizeCommon.COUPON_ALNU_NO_SIZE);
		}

		return alphanumericNO;
	}

}
