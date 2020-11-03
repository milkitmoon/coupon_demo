package com.milkit.app.domain.couponlog.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milkit.app.api.coupon.request.ApprRequest;
import com.milkit.app.api.coupon.request.UseApprRequest;
import com.milkit.app.common.ErrorCodeEnum;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.publish.service.CouponPublishHandlerServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.couponlog.CouponLogSupplier;
import com.milkit.app.enumer.TradeDivEnum;
import com.milkit.app.enumer.TradeTypeEnum;
import com.milkit.app.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CouponLogManagerServiceImpl {
	
	@Autowired
	private ApprNoGeneratorServiceImpl apprNoGeneratorService;
	
	@Autowired
	private CouponLogServiceImpl couponLogService;


	public <T extends ApprRequest> CouponLog getProtoCouponLog(Coupon coupon, T t,  CouponLogSupplier couponLogSupplier) throws Exception {
		return couponLogSupplier.addedCouponLog(t, coupon, generateProtoCouponLog(coupon, t));
	}
	
	public void insertCouponLog(CouponLog couponLog) {
		try {
			CouponLog ressult = couponLogService.insert(couponLog);
			
log.debug(ressult.toString());
		} catch(Exception ex) {
			log.trace(ex.getMessage(), ex);
		}
	}
	
	public <T extends ApprRequest> void insertCouponLog(CouponLogSupplier couponLogSupplier, Coupon coupon, T t, String resultCD) throws Exception {
		CouponLog genCouponLog = couponLogSupplier.addedCouponLog(t, coupon, generateProtoCouponLog(coupon, t));
		genCouponLog.setResultCD(resultCD);
		
		insertCouponLog(genCouponLog);
	}

	
	private <T extends ApprRequest> CouponLog generateProtoCouponLog(Coupon coupon, T t) throws ServiceException {
		Date apprTime = new Date();
		
		CouponLog couponLog = null;
		try {
			couponLog = CouponLog
			.builder()
			.saleDT(DateUtil.getTimeString(apprTime, "yyyyMMdd"))
			.couponNO(coupon.getCouponNO())
			.userID(t.getUserID())
			.tradeType(TradeTypeEnum.NORMAL.getValue())
			.apprNO(apprNoGeneratorService.generateApprNo(apprTime, coupon.getCouponSeq()))
			.apprTime(apprTime)
			.faceAmt(coupon.getFaceAmt())
			.pubAmt(coupon.getPubAmt())
			.resultCD(ErrorCodeEnum.OK.getCode())
			.instTime(apprTime)
			.updTime(apprTime)
			.instUser(coupon.getUserID())
			.updUser(coupon.getUserID())
			.build();
		} catch (Exception ex) {
			throw new ServiceException(ErrorCodeEnum.GenerateCouponLogException.getCode());
		}
		
		return couponLog;
	}

	public CouponLog getCouponLogByApprNO(String apprNO) throws Exception {
		return couponLogService.getCouponLogByApprNO(apprNO);
	}



}
