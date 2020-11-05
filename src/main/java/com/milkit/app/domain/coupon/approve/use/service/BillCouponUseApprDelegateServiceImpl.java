package com.milkit.app.domain.coupon.approve.use.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milkit.app.api.coupon.request.UseApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.api.coupon.response.BillUseApprResponse;
import com.milkit.app.common.ErrorCodeEnum;
import com.milkit.app.common.exception.CouponServiceException;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.approve.CouponApproveDelegateService;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.couponlog.CouponLogSupplier;
import com.milkit.app.domain.couponlog.service.CouponLogManagerServiceImpl;
import com.milkit.app.enumer.CouponStatusEnum;
import com.milkit.app.enumer.TradeDivEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BillCouponUseApprDelegateServiceImpl implements CouponApproveDelegateService<UseApprRequest, BillUseApprResponse>, CouponLogSupplier<UseApprRequest> {
	
	@Autowired
    private CouponServiceImpl couponService;
	

	@Override
	public BillUseApprResponse approve(UseApprRequest apprRequest, Coupon coupon, CouponLog couponLog) throws Exception {
		BillUseApprResponse billUseApprResponse = new BillUseApprResponse(couponLog);

		coupon.setUseAmt(couponLog.getUseAmt());
		coupon.setUseTime(couponLog.getApprTime());
		coupon.setStatus(CouponStatusEnum.USE.getValue());
		coupon.setUpdTime(new Date());
		coupon.setUpdUser(apprRequest.getUserID());
		
		billUseApprResponse.setFaceAmt(couponLog.getFaceAmt());
		billUseApprResponse.setUseAmt(couponLog.getUseAmt());
		billUseApprResponse.setChangeAmt(couponLog.getChangeAmt());

		Coupon updCoupon = couponService.update(coupon);
//log.debug("updCoupon:"+updCoupon.toString());
		
		return billUseApprResponse;
	}

	@Override
	public CouponLog addedCouponLog(UseApprRequest apprRequest, Coupon coupon, CouponLog couponLog) throws ServiceException {

		try {
			couponLog.setTradeDiv(TradeDivEnum.USE.getValue());
			
			long faceAmt = coupon.getFaceAmt();
			long reqUseAmt = apprRequest.getReqUseAmt();
			long useAmt = getAvailUseAmt(faceAmt, reqUseAmt);		//사용가능금액으로 조정
			long changeAmt = getAvailChangeAmt(faceAmt, useAmt, coupon.getCouponInfo().getChangeMinUseRate());
			
			couponLog.setReqUseAmt(reqUseAmt);
			couponLog.setUseAmt(useAmt);
			couponLog.setChangeAmt(changeAmt);
		} catch (Exception ex) {
//ex.printStackTrace();
			throw new CouponServiceException(ErrorCodeEnum.GenerateCouponLogException.getCode());
		}
		
		return couponLog;
	}


	private long getAvailUseAmt(long faceAmt, long reqUseAmt) {
		long useAmt = reqUseAmt;
		
		if(reqUseAmt > faceAmt) {
			useAmt = faceAmt;
		}
		
		return useAmt;
	}

	private long getAvailChangeAmt(long faceAmt, long useAmt, float changeMinUseRate) {
		long changeAmt = (faceAmt-useAmt);
		
		BigDecimal maxAvailChangeAmtDeci = (new BigDecimal(faceAmt)).multiply( ((new BigDecimal(100).subtract(new BigDecimal(changeMinUseRate))).divide(new BigDecimal(100))) );	// faceAmt * ((100 - changeMinUseRate))/100
		long maxAvailChangeAmt = maxAvailChangeAmtDeci.longValue();
		
		if(changeAmt > maxAvailChangeAmt) {
			changeAmt = maxAvailChangeAmt;
		}

		return changeAmt;
	}
	
}
