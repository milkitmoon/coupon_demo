package com.milkit.app.domain.couponlog;

import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponinfo.CouponInfo;

public interface CouponLogSupplier<T> {
	
	public CouponLog addedCouponLog(T t, Coupon coupon, CouponLog couponLog) throws ServiceException;
	
}
