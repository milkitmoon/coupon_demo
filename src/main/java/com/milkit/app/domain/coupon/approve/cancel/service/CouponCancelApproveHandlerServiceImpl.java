package com.milkit.app.domain.coupon.approve.cancel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.milkit.app.api.coupon.request.ApprRequest;
import com.milkit.app.api.coupon.request.CancelApprRequest;
import com.milkit.app.api.coupon.request.UseApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.common.ErrorCodeEnum;
import com.milkit.app.common.exception.CouponServiceException;
import com.milkit.app.common.exception.ServiceException;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.approve.AbstractCouponApproveHandlerServiceImpl;
import com.milkit.app.domain.coupon.approve.CouponApproveDelegateService;
import com.milkit.app.domain.coupon.publish.service.AutoPublishCouponDelegateServiceImpl;
import com.milkit.app.domain.coupon.publish.service.CouponPublishDelegateService;
import com.milkit.app.domain.coupon.publish.service.CouponPublishHandlerServiceImpl;
import com.milkit.app.domain.coupon.service.CouponServiceImpl;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.couponlog.CouponLogSupplier;
import com.milkit.app.domain.couponlog.service.CouponLogManagerServiceImpl;
import com.milkit.app.enumer.CouponDivEnum;
import com.milkit.app.enumer.CouponStatusEnum;
import com.milkit.app.enumer.OperateStatusEnum;
import com.milkit.app.enumer.PubDivEnum;
import com.milkit.app.enumer.TradeDivEnum;
import com.milkit.app.enumer.TradeTypeEnum;
import com.milkit.app.enumer.YNEnum;
import com.milkit.app.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CouponCancelApproveHandlerServiceImpl extends AbstractCouponApproveHandlerServiceImpl<CancelApprRequest, ApprResponse> {
	
	@Override
	protected CouponApproveDelegateService<CancelApprRequest, ?> getCouponApproveDelegateService(String couponDiv) throws Exception {
		CouponApproveDelegateService<CancelApprRequest, ?> couponApproveDelegateService = null;

		if(couponDiv.equals(CouponDivEnum.BILL_COUPON.getValue())) {
			couponApproveDelegateService = billCouponCancelApproveDelegateService();
		} else if(couponDiv.equals(CouponDivEnum.DC_COUPON.getValue())) {
			couponApproveDelegateService = dcCouponCancelApproveDelegateService();
		} else {
			throw new CouponServiceException(ErrorCodeEnum.NotExistCouponDivException.getCode(), new String[]{couponDiv});
		}
		
		return couponApproveDelegateService;
	}
	
	@Bean
    public CouponApproveDelegateService<CancelApprRequest, ?> billCouponCancelApproveDelegateService() throws Exception {
        return new BillCouponCancelApproveDelegateServiceImpl();
    }
	
	@Bean
    public CouponApproveDelegateService<CancelApprRequest, ?> dcCouponCancelApproveDelegateService() throws Exception {
        return new DcCouponCancelApproveDelegateServiceImpl();
    }
	

	@Override
	protected void addedValidate(CancelApprRequest apprRequest, Coupon coupon) throws Exception {
		CouponLog cxlCouponLog = apprRequest.getCxlCouponLog();
		
		if(cxlCouponLog == null) {
			throw new CouponServiceException(ErrorCodeEnum.NotExistCxlCouponLogException.getCode(), new String[]{cxlCouponLog.getApprNO()});
		}
		if( !coupon.getCouponNO().equals(cxlCouponLog.getCouponNO()) ) {				//취소대상 거래의 쿠폰번호와 취소요청 쿠폰번호 확인 
			throw new CouponServiceException(ErrorCodeEnum.IncorrectCancelCouponNumberExeption.getCode(), new String[]{cxlCouponLog.getCouponNO(), cxlCouponLog.getApprNO()});
		}
		if( cxlCouponLog.getTradeType().equals(TradeTypeEnum.CANCEL.getValue()) ) {		//기 취소거래건은 취소할 수 없음
			throw new CouponServiceException(ErrorCodeEnum.UnableCanceledCancelTradeException.getCode(), new String[]{cxlCouponLog.getApprNO(), cxlCouponLog.getTradeType()});
		}
		if( !cxlCouponLog.getTradeDiv().equals(TradeDivEnum.USE.getValue()) ) {			//사용 거래만 취소할 수 있음 (발행, 조회, 폐기 거래는 취소 불가)
			throw new CouponServiceException(ErrorCodeEnum.InvalidCancelTradeDivException.getCode(), new String[]{cxlCouponLog.getTradeDiv()});
		}
		if( cxlCouponLog.getCxlApprNO() != null && !cxlCouponLog.getCxlApprNO().equals("") ) {			//이미 취소된 쿠폰은 취소할 수 없음
			throw new CouponServiceException(ErrorCodeEnum.AlreadyCancelApprNoException.getCode(), new String[]{cxlCouponLog.getApprNO()});
		}
		
		String status = coupon.getStatus();
		if( status == null || !status.equals(CouponStatusEnum.USE.getValue()) ) {			//쿠폰상태가 사용완료된 상태가 아닌 쿠폰은 취소할 수 없음
			throw new CouponServiceException(ErrorCodeEnum.UnableCancelCouponStatusException.getCode(), new String[]{coupon.getCouponNO()});
		}
		
		if( !cxlCouponLog.getResultCD().equals(ErrorCodeEnum.OK.getCode()) ) {		//취소할 거래정보의 결과가 성공이 아닌 경우는 취소할 수 없음	
			throw new CouponServiceException(ErrorCodeEnum.InvalidCxlCouponLogResultException.getCode(), new String[]{cxlCouponLog.getApprNO(), cxlCouponLog.getResultCD()});
		}
	}

	
}
