package com.milkit.app.domain.coupon.approve.cancel.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milkit.app.api.coupon.request.CancelApprRequest;
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
import com.milkit.app.enumer.TradeTypeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BillCouponCancelApproveDelegateServiceImpl implements CouponApproveDelegateService<CancelApprRequest, BillUseApprResponse>, CouponLogSupplier<CancelApprRequest> {
	
	@Autowired
    private CouponServiceImpl couponService;
	
	@Autowired
	private CouponLogManagerServiceImpl couponLogManagerService;

	@Override
	public BillUseApprResponse approve(CancelApprRequest apprRequest, Coupon coupon, CouponLog couponLog) throws Exception {
		BillUseApprResponse billUseApprResponse = new BillUseApprResponse(couponLog);

		Date currDate = new Date();
		coupon.setUseTime(null);
		coupon.setUseAmt(0l);
		coupon.setStatus(CouponStatusEnum.PUBLISH.getValue());
		coupon.setUpdTime(currDate);
		coupon.setUpdUser(apprRequest.getUserID());
		
		billUseApprResponse.setFaceAmt(couponLog.getFaceAmt());
		billUseApprResponse.setUseAmt(couponLog.getUseAmt());
		billUseApprResponse.setChangeAmt(couponLog.getChangeAmt());

		CouponLog cxlCouponLog = apprRequest.getCxlCouponLog();
		cxlCouponLog.setCxlApprNO(couponLog.getApprNO());
		cxlCouponLog.setUpdTime(currDate);
		cxlCouponLog.setUpdUser(apprRequest.getUserID());
		Coupon updCoupon = couponService.updateReturn(coupon, cxlCouponLog);		//취소 원거래 로그에 취소거래의 거래번호를 등록
log.debug("updCoupon:"+updCoupon.toString());		
		
		return billUseApprResponse;
	}


	public CouponLog addedCouponLog(CancelApprRequest apprRequest, Coupon coupon, CouponLog couponLog) throws ServiceException {
		try {
			couponLog.setTradeType(TradeTypeEnum.CANCEL.getValue());

			String cxlApprNO = apprRequest.getApprNO();
			CouponLog cxlCouponLog = couponLogManagerService.getCouponLogByApprNO(cxlApprNO);

			if(cxlCouponLog != null) {
				apprRequest.setCxlCouponLog(cxlCouponLog);
				couponLog.setTradeDiv(cxlCouponLog.getTradeDiv());
				couponLog.setProductCD(cxlCouponLog.getProductCD());
			
				couponLog.setReqUseAmt( cxlCouponLog.getReqUseAmt()*-1 );
				couponLog.setUseAmt( cxlCouponLog.getUseAmt()*-1 );
				couponLog.setChangeAmt( cxlCouponLog.getChangeAmt()*-1 );

				couponLog.setCxlApprNO( cxlApprNO );		//취소로그에 취소요청 원거래번호 등록
			}

		} catch (Exception ex) {
ex.printStackTrace();
			throw new CouponServiceException(ErrorCodeEnum.GenerateCouponLogException.getCode());
		}
		

		return couponLog;
	}


	
}
