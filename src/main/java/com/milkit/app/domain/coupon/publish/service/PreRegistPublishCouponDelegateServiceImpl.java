package com.milkit.app.domain.coupon.publish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milkit.app.api.coupon.request.PublishRequest;
import com.milkit.app.common.ErrorCodeEnum;
import com.milkit.app.common.exception.CouponServiceException;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.enumer.CouponStatusEnum;

@Component
public class PreRegistPublishCouponDelegateServiceImpl implements CouponPublishDelegateService {

	@Autowired
	private CouponServiceImpl couponService;
	
	@Override
	public Coupon publish(CouponInfo couponInfo, PublishRequest publishRequest) throws Exception {
		String couponCD = couponInfo.getCouponCD();
		Coupon resultCoupon = couponService.getPreRegistCoupon(couponCD);
		
		if(resultCoupon == null) {
			throw new CouponServiceException(ErrorCodeEnum.NotExistPreRegistCouponException.getCode(), new String[]{couponCD});
		}
		resultCoupon.setCouponNM(couponInfo.getCouponNM());
		resultCoupon.setCouponDiv(couponInfo.getCouponDiv());
		resultCoupon.setUserID(publishRequest.getUserID());
		resultCoupon.setStatus(CouponStatusEnum.PUBLISH.getValue());
		
		couponService.publishPreRegistCoupon(resultCoupon);
		
		return resultCoupon;
	}

}
