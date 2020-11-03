package com.milkit.app.domain.coupon.approve;

import com.milkit.app.api.coupon.request.ApprRequest;
import com.milkit.app.api.coupon.request.UseApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.domain.coupon.Coupon;

public interface CouponApproveHandlerService<T, K> {

	public K approve(T t) throws Exception;


}
