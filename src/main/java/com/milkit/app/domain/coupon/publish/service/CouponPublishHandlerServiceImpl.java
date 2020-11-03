package com.milkit.app.domain.coupon.publish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.milkit.app.api.coupon.request.PublishRequest;
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
import com.milkit.app.enumer.OperateStatusEnum;
import com.milkit.app.enumer.PubDivEnum;
import com.milkit.app.enumer.TradeDivEnum;
import com.milkit.app.enumer.YNEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CouponPublishHandlerServiceImpl implements CouponApproveHandlerService<PublishRequest, Coupon>, CouponLogSupplier<PublishRequest> {
	
	@Autowired
    private CouponInfoServiceImpl couponInfoService;
	
	@Autowired
    private CouponServiceImpl couponService;

	
	@Autowired
	private CouponLogManagerServiceImpl couponLogManagerService;
	
	

	public Coupon approve(PublishRequest publishRequest) throws Exception {
		CouponInfo couponInfo = couponInfoService.getCouponInfo(publishRequest.getCouponCD());
		
		validate(publishRequest, couponInfo);
		
		Coupon publishCoupon = publish(couponInfo, publishRequest);
		
		couponLogManagerService.insertCouponLog(this, publishCoupon, publishRequest, ErrorCodeEnum.OK.getCode());
		
		return publishCoupon;
	}


	private void validate(PublishRequest publishRequest, CouponInfo couponInfo) throws Exception {

		if(couponInfo == null) {
			throw new CouponServiceException(ErrorCodeEnum.NotExistCouponInfoException.getCode(), new String[]{publishRequest.getCouponCD()});
		}
		
		String useYN = couponInfo.getUseYN();
		if(useYN == null || !useYN.equals(YNEnum.YES.getValue())) {
			throw new CouponServiceException(ErrorCodeEnum.NotUseCouponInfoException.getCode());
		}
		
		String operateStatus = couponInfo.getOperateStatus();
		if(operateStatus == null || !operateStatus.equals(OperateStatusEnum.CONFIRM.getValue())) {
			throw new CouponServiceException(ErrorCodeEnum.OperateStatusNotConfirmException.getCode(), new String[]{String.valueOf(operateStatus)});
		}
		
		int maxPubQty = couponInfo.getMaxPubQty();
		int publishedCouponQty  = couponService.getPublishedCouponQty(publishRequest.getCouponCD());
		
		if(maxPubQty != 0 && publishedCouponQty >= maxPubQty) {
			throw new CouponServiceException(ErrorCodeEnum.MaxPubQtyException.getCode(), new String[]{String.valueOf(maxPubQty), String.valueOf(publishedCouponQty)});
		}
	}

	private Coupon publish(CouponInfo couponInfo, PublishRequest publishRequest) throws Exception {
		CouponPublishDelegateService couponPublishDelegateService = null;
		
		String pubDiv = couponInfo.getPubDiv();
		if(pubDiv.equals(PubDivEnum.AUTO_PUBLISH.getValue())) {
			couponPublishDelegateService = autoPublishCouponDelegateService();
		} else if(pubDiv.equals(PubDivEnum.PRE_REGIST.getValue())) {
			couponPublishDelegateService = preRegistPublishCouponDelegateService();
		} else {
			throw new ServiceException(ErrorCodeEnum.NotExistPubDivException.getCode(), new String[]{pubDiv});
		}

		return couponPublishDelegateService.publish(couponInfo, publishRequest);
	}

	@Bean
    public CouponPublishDelegateService autoPublishCouponDelegateService() throws Exception {
        return new AutoPublishCouponDelegateServiceImpl();
    }
    
    @Bean
    public CouponPublishDelegateService preRegistPublishCouponDelegateService() throws Exception {
        return new PreRegistPublishCouponDelegateServiceImpl();
    }


	@Override
	public CouponLog addedCouponLog(PublishRequest publishRequest, Coupon coupon, CouponLog couponLog) {
		couponLog.setTradeDiv(TradeDivEnum.PUBLISH.getValue());
		
		return couponLog;
	}

}
