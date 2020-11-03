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
public class PublishRequest extends ApprRequest {
	
    @ApiModelProperty(value="쿠폰코드")
    private String couponCD;
	
    public PublishRequest(String userID, String couponCD) {
    	super.setUserID(userID);
    	this.couponCD = couponCD;
    }
    
    public PublishRequest(String userID, String couponNO, String couponCD) {
    	super.setUserID(userID);
    	super.setCouponNO(couponNO);
    	this.couponCD = couponCD;
    }

	
}
