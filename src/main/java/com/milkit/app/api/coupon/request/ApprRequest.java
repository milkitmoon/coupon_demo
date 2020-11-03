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
public class ApprRequest {
	
    @ApiModelProperty(value="쿠폰업무계정")
    private String userID;

    @ApiModelProperty(value="쿠폰번호")
    private String couponNO;
    
    
    public ApprRequest(String userID, String couponNO) {
    	this.userID = userID;
    	this.couponNO = couponNO;
    }


	
}
