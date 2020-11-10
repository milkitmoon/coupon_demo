package com.milkit.app.domain.coupon.query.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.milkit.app.api.coupon.request.ApprRequest;
import com.milkit.app.api.coupon.request.PublishRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.api.coupon.response.BillUseApprResponse;
import com.milkit.app.common.ErrorCodeEnum;
import com.milkit.app.common.exception.CouponServiceException;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.approve.CouponApproveHandlerService;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponinfo.service.CouponInfoServiceImpl;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.couponlog.CouponLogSupplier;
import com.milkit.app.domain.couponlog.service.CouponLogManagerServiceImpl;
import com.milkit.app.domain.couponlog.service.CouponLogServiceImpl;
import com.milkit.app.enumer.CouponStatusEnum;
import com.milkit.app.enumer.OperateStatusEnum;
import com.milkit.app.enumer.PubDivEnum;
import com.milkit.app.enumer.TradeDivEnum;
import com.milkit.app.enumer.YNEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CouponQueryHandlerServiceImpl implements CouponApproveHandlerService<ApprRequest, Coupon>, CouponLogSupplier<ApprRequest> {

	
	@Autowired
    private CouponServiceImpl couponService;
	
	@Autowired
	private CouponLogManagerServiceImpl couponLogManagerService;
	
	

	public Coupon approve(ApprRequest apprRequest) throws Exception {
		Coupon coupon = couponService.getCoupon(apprRequest.getCouponNO());
		CouponInfo couponInfo = coupon.getCouponInfo();
		if(couponInfo == null) {
			throw new ServiceException(ErrorCodeEnum.NotExistCouponInfoException.getCode(), new String[]{coupon.getCouponCD()});
		}
		
		CouponLog couponLog = couponLogManagerService.getProtoCouponLog(coupon, apprRequest, this);
		
		try {
			validate(apprRequest, couponInfo, coupon);
			
			return query(coupon);
			
		} catch(ServiceException svee) {
			couponLog.setResultCD(svee.getCode());
			throw svee;
		} catch(Exception ee) {
			couponLog.setResultCD(ErrorCodeEnum.SystemError.getCode());
			throw ee;
		} finally {
			couponLogManagerService.insertCouponLog(couponLog);
		}
	}

	private Coupon query(Coupon coupon) {
		coupon.setCouponNM(coupon.getCouponInfo().getCouponNM());
		coupon.setCouponDiv(coupon.getCouponInfo().getCouponDiv());
		return coupon;
	}

	private void validate(ApprRequest apprRequest, CouponInfo couponInfo, Coupon coupon) throws Exception {
		String useYN = couponInfo.getUseYN();
		if(useYN == null || !useYN.equals(YNEnum.YES.getValue())) {
			throw new CouponServiceException(ErrorCodeEnum.NotUseCouponInfoException.getCode());
		}
		
		String operateStatus = couponInfo.getOperateStatus();
		if(operateStatus == null || !operateStatus.equals(OperateStatusEnum.CONFIRM.getValue())) {
			throw new CouponServiceException(ErrorCodeEnum.OperateStatusNotConfirmException.getCode(), new String[]{String.valueOf(operateStatus)});
		}
	}

	
	@Override
	public CouponLog addedCouponLog(ApprRequest apprRequest, Coupon coupon, CouponLog couponLog) {
		couponLog.setTradeDiv(TradeDivEnum.QUERY.getValue());
		
		return couponLog;
	}

}
