package com.milkit.app.api.coupon.response;

import java.util.Date;

import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponlog.CouponLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@ApiModel
public class ApprResponse {

	@ApiModelProperty(value="해당거래의 거래번호")
	private String apprNO;
	
	@ApiModelProperty(value="승인시간")
	private Date apprTime;
	

	public ApprResponse(String apprNO, Date apprTime) {
		this.apprNO = apprNO;
		this.apprTime = apprTime;
	}
	
	
}
