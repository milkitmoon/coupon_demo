package com.milkit.app.domain.coupon.approve.use.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milkit.app.api.coupon.request.UseApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.api.coupon.response.BillUseApprResponse;
import com.milkit.app.api.coupon.response.DcUseApprResponse;
import com.milkit.app.common.ErrorCodeEnum;
import com.milkit.app.common.exception.CouponServiceException;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.approve.CouponApproveDelegateService;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.couponlog.CouponLogSupplier;
import com.milkit.app.enumer.CouponStatusEnum;
import com.milkit.app.enumer.TradeDivEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DcCouponApproveDelegateServiceImpl implements CouponApproveDelegateService<UseApprRequest, DcUseApprResponse>, CouponLogSupplier<UseApprRequest> {

	@Autowired
    private CouponServiceImpl couponService;
	
	@Override
	public DcUseApprResponse approve(UseApprRequest apprRequest, Coupon coupon, CouponLog couponLog) throws Exception {
		DcUseApprResponse useApprResponse = new DcUseApprResponse(couponLog);
		
		long reqUseAmt = apprRequest.getReqUseAmt();
		long dcPossMinAmt = coupon.getCouponInfo().getDcPossMinAmt();
		if(dcPossMinAmt > 0 && reqUseAmt < dcPossMinAmt) {
			throw new ServiceException(ErrorCodeEnum.DcPossMinAmtException.getCode(), new String[]{String.valueOf(dcPossMinAmt), String.valueOf(reqUseAmt)});
		}
		
		coupon.setUseAmt(couponLog.getUseAmt());
		coupon.setUseTime(couponLog.getApprTime());
		coupon.setStatus(CouponStatusEnum.USE.getValue());
		coupon.setUpdTime(new Date());
		coupon.setUpdUser(apprRequest.getUserID());
		
		useApprResponse.setDcRate(coupon.getDcRate());
		useApprResponse.setDcAmt(couponLog.getDcAmt());
		useApprResponse.setUseAmt(couponLog.getUseAmt());


		Coupon updCoupon = couponService.update(coupon);
		
		return useApprResponse;
	}

	@Override
	public CouponLog addedCouponLog(UseApprRequest apprRequest, Coupon coupon, CouponLog couponLog) throws ServiceException {
		try {
			couponLog.setTradeDiv(TradeDivEnum.USE.getValue());
			long reqUseAmt = apprRequest.getReqUseAmt();
			long dcAmt = getAvailDcAmt(coupon, reqUseAmt);		//할인가능금액으로 조정
			long useAmt = reqUseAmt - dcAmt;
			
			couponLog.setReqUseAmt(reqUseAmt);
			couponLog.setDcAmt(dcAmt);
			couponLog.setUseAmt(useAmt);
		} catch (Exception ex) {
			throw new CouponServiceException(ErrorCodeEnum.GenerateCouponLogException.getCode());
		}
		return couponLog;
	}
	
	private long getAvailDcAmt(Coupon coupon, long reqUseAmt) {
		float dcRate = coupon.getDcRate();
		BigDecimal dcAmtDeci = (new BigDecimal(reqUseAmt)).multiply(BigDecimal.valueOf(dcRate/100));
		
		long dcAmt = dcAmtDeci.longValue();
		long dcMaxAmt = coupon.getCouponInfo().getDcMaxAmt();
		
		if(dcAmt > dcMaxAmt) {
			dcAmt = dcMaxAmt;
		}
		
		return dcAmt;
	}

}
