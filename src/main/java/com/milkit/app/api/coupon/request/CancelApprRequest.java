package com.milkit.app.api.coupon.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponlog.CouponLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@ApiModel
public class CancelApprRequest extends ApprRequest {

	@ApiModelProperty(value="취소요청할 거래번호")
	private String apprNO;
	
	@JsonIgnore
	private CouponLog cxlCouponLog;
	

    public CancelApprRequest(String userID, String couponNO, String apprNO) {
    	this.setUserID(userID);
    	this.setCouponNO(couponNO);
    	this.apprNO = apprNO;
    }
	
	
}
