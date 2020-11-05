package com.milkit.app.domain.coupon.approve.use.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.milkit.app.api.coupon.request.ApprRequest;
import com.milkit.app.api.coupon.request.UseApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.common.ErrorCodeEnum;
import com.milkit.app.common.exception.CouponServiceException;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.approve.AbstractCouponApproveHandlerServiceImpl;
import com.milkit.app.domain.coupon.approve.CouponApproveDelegateService;
import com.milkit.app.domain.coupon.approve.CouponApproveHandlerService;
import com.milkit.app.domain.coupon.publish.service.AutoPublishCouponDelegateServiceImpl;
import com.milkit.app.domain.coupon.publish.service.CouponPublishDelegateService;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.couponlog.CouponLogSupplier;
import com.milkit.app.domain.couponlog.service.CouponLogManagerServiceImpl;
import com.milkit.app.enumer.CouponDivEnum;
import com.milkit.app.enumer.CouponStatusEnum;
import com.milkit.app.enumer.OperateStatusEnum;
import com.milkit.app.enumer.PubDivEnum;
import com.milkit.app.enumer.YNEnum;
import com.milkit.app.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CouponUseApproveHandlerServiceImpl extends AbstractCouponApproveHandlerServiceImpl<UseApprRequest, ApprResponse> {
	
	
	@Override
	protected CouponApproveDelegateService<UseApprRequest, ? extends ApprResponse> getCouponApproveDelegateService(String couponDiv) throws Exception {
		CouponApproveDelegateService<UseApprRequest, ? extends ApprResponse> couponApproveDelegateService = null;
		
		if(couponDiv.equals(CouponDivEnum.BILL_COUPON.getValue())) {
			couponApproveDelegateService = billCouponApproveDelegateService();
		} else if(couponDiv.equals(CouponDivEnum.DC_COUPON.getValue())) {
			couponApproveDelegateService = dcCouponApproveDelegateService();
		} else {
			throw new ServiceException(ErrorCodeEnum.NotExistCouponDivException.getCode(), new String[]{couponDiv});
		}
		
		return couponApproveDelegateService;
	}
	
	
	@Bean
    public CouponApproveDelegateService<UseApprRequest, ? extends ApprResponse> billCouponApproveDelegateService() throws Exception {
        return new BillCouponUseApprDelegateServiceImpl();
    }
	
	@Bean
    public CouponApproveDelegateService<UseApprRequest, ? extends ApprResponse> dcCouponApproveDelegateService() throws Exception {
        return new DcCouponUseApproveDelegateServiceImpl();
    }

	@Override
	protected void addedValidate(UseApprRequest apprRequest, Coupon coupon) throws Exception {
		String status = coupon.getStatus();
		if(coupon != null && status.equals(CouponStatusEnum.USE.getValue())) {			//이미 사용상태의 쿠폰은 사용승인할 수 없음
			throw new CouponServiceException(ErrorCodeEnum.CouponAlreadyUseException.getCode(), new String[]{status});
		}
		
		if(status == null || !status.equals(CouponStatusEnum.PUBLISH.getValue())) {		//발행상태의 쿠폰은 사용승인할 수 없음
			throw new CouponServiceException(ErrorCodeEnum.CouponStatusNotPublishException.getCode(), new String[]{status});
		}
		
		if( !(apprRequest.getReqUseAmt() > 0) ) {		//사용요청 금액은 0보다 커야함
			throw new CouponServiceException(ErrorCodeEnum.CouponReqUseAmtException.getCode(), new String[]{String.valueOf(apprRequest.getReqUseAmt())});
		}
	}


}
