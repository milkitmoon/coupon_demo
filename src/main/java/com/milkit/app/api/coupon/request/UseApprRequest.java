package com.milkit.app.api.coupon.request;

import java.util.Date;

import com.milkit.app.domain.coupon.Coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@ApiModel
public class UseApprRequest extends ApprRequest {

    @ApiModelProperty(value="사용요청금액")
    private long reqUseAmt;
	

    public UseApprRequest(String userID, String couponNO, Long reqUseAmt) {
    	this.setUserID(userID);
    	this.setCouponNO(couponNO);
    	this.reqUseAmt = reqUseAmt;
    }
	
	
}
