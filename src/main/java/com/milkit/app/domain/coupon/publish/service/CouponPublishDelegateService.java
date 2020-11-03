package com.milkit.app.domain.coupon.publish.service;

import com.milkit.app.api.coupon.request.PublishRequest;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponinfo.CouponInfo;

public interface CouponPublishDelegateService {

	public Coupon publish(CouponInfo couponInfo, PublishRequest publishRequest) throws Exception;

}
