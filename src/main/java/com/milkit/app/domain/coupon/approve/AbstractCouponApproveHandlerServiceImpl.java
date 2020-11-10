package com.milkit.app.domain.coupon.approve;

import org.springframework.beans.factory.annotation.Autowired;

import com.milkit.app.api.coupon.request.ApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.common.ErrorCodeEnum;
import com.milkit.app.common.exception.CouponServiceException;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.couponlog.CouponLogSupplier;
import com.milkit.app.domain.couponlog.service.CouponLogManagerServiceImpl;
import com.milkit.app.enumer.OperateStatusEnum;
import com.milkit.app.enumer.YNEnum;
import com.milkit.app.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCouponApproveHandlerServiceImpl<T extends ApprRequest, K extends ApprResponse> implements CouponApproveHandlerService<T, K> {
	
	@Autowired
    private CouponServiceImpl couponService;
	
	@Autowired
	private CouponLogManagerServiceImpl couponLogManagerService;
	

	public K approve(T t) throws Exception {
		Coupon coupon = couponService.getCoupon(t.getCouponNO());
		CouponInfo couponInfo = coupon.getCouponInfo();
		if(couponInfo == null) {
			throw new ServiceException(ErrorCodeEnum.NotExistCouponInfoException.getCode(), new String[]{coupon.getCouponCD()});
		}
		
		CouponApproveDelegateService<T, ? extends K> couponApproveDelegateService = getCouponApproveDelegateService(couponInfo.getCouponDiv());
		CouponLog couponLog = couponLogManagerService.getProtoCouponLog(coupon, t, (CouponLogSupplier<T>)couponApproveDelegateService);

		try {
			validate(t, coupon);
			
			return couponApproveDelegateService.approve(t, coupon, couponLog);
			
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
	
	protected void validate(T t, Coupon coupon) throws Exception {

		CouponInfo couponInfo = coupon.getCouponInfo();
		String couponInfoUseYN = couponInfo.getUseYN();
		if(couponInfoUseYN == null || !couponInfoUseYN.equals(YNEnum.YES.getValue())) {
			throw new CouponServiceException(ErrorCodeEnum.NotUseCouponInfoException.getCode());
		}
		
		String operateStatus = couponInfo.getOperateStatus();
		if(operateStatus == null || !operateStatus.equals(OperateStatusEnum.CONFIRM.getValue())) {
			throw new CouponServiceException(ErrorCodeEnum.OperateStatusNotConfirmException.getCode(), new String[]{operateStatus});
		}

		String useYN = coupon.getUseYN();
		if(useYN == null || !useYN.equals(YNEnum.YES.getValue())) {
			throw new CouponServiceException(ErrorCodeEnum.NotUseCouponException.getCode());
		}
		
		checkApprDT(coupon);
		
		addedValidate(t, coupon);
	}
	
	private void checkApprDT(Coupon coupon) throws Exception {
		String apprStartDT = coupon.getApprStartDT();
		String apprEndDT = coupon.getApprEndDT();
		String currDT = DateUtil.getCurrectTimeString("yyyyMMdd");
		
		if( (apprStartDT != null && !apprStartDT.equals("") &&
				Integer.parseInt(currDT) < Integer.parseInt(apprStartDT))
									||
			(apprEndDT != null && !apprEndDT.equals("") &&
				Integer.parseInt(currDT) > Integer.parseInt(apprEndDT)) ) {
			throw new CouponServiceException(ErrorCodeEnum.CouponApprExpireTimeException.getCode(), new String[]{apprStartDT, apprEndDT});
		}
		
	}
	
	
	abstract protected CouponApproveDelegateService<T, ? extends K> getCouponApproveDelegateService(String couponDiv) throws Exception;
	
	abstract protected void addedValidate(T t, Coupon coupon) throws Exception;
	
}
