package com.milkit.app.domain.coupon.approve;

import com.milkit.app.api.coupon.request.ApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.couponlog.CouponLogSupplier;

public interface CouponApproveDelegateService<K extends ApprRequest, T extends ApprResponse> {

	T approve(K k, Coupon coupon, CouponLog couponLog) throws Exception;

}
