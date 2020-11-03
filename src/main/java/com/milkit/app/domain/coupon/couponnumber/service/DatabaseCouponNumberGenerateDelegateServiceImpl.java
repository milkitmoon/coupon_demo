package com.milkit.app.domain.coupon.couponnumber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milkit.app.common.CouponSizeCommon;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.service.CouponNOChecksumServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponnoseq.service.CouponNoSeqServiceImpl;
import com.milkit.app.util.StringUtil;

@Component
public class DatabaseCouponNumberGenerateDelegateServiceImpl implements CouponNumberGenerateDelegateService {
	
	@Autowired
    private CouponNoSeqServiceImpl couponNoSeqService;
	
	@Autowired
    private CouponNOChecksumServiceImpl couponNOChecksumService;

	@Override
	public String generateCouponNumber(CouponInfo couponInfo) throws Exception {
		
		String couponCD = couponInfo.getCouponCD();
		long result = couponNoSeqService.selectNextCouponnoSeq(couponCD);
		
		StringBuilder builder = new StringBuilder();
		builder
		.append(couponCD)
		.append( StringUtil.leftPad(String.valueOf(result), "0", CouponSizeCommon.COUPON_SEQ_SIZE) );
		
		String checksum = couponNOChecksumService.getChecksum(builder.toString());
		
		builder.append(checksum);
		
		
		return builder.toString();
	}

}
