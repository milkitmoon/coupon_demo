package com.milkit.app.domain.coupon.couponnumber.service;

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
log.debug("checksum:"+checksum);
		builder.append(checksum);

		String numericNO = builder.toString();
log.debug("numericNO:"+numericNO);
		couponNO = convertAlphanumericNO(numericNO);
		
		return couponNO;
	}

	private String convertAlphanumericNO(String numericNO) {
		String couponNO = null;
		String alphanumericNO = Long.toString(Long.valueOf(numericNO), 36);

		if(alphanumericNO != null && alphanumericNO.length() < CouponSizeCommon.COUPON_ALNU_NO_SIZE) {
			couponNO = StringUtil.leftPad(String.valueOf(alphanumericNO), "0", CouponSizeCommon.COUPON_ALNU_NO_SIZE);
		}

		return couponNO;
	}

}
