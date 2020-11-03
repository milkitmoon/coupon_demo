package com.milkit.app.domain.coupon.couponnumber.service;

import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponinfo.CouponInfo;

public interface CouponNumberGenerateDelegateService {

	public String generateCouponNumber(CouponInfo couponInfo) throws Exception;

}
