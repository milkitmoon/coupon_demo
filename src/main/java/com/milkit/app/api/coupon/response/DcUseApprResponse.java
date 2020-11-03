package com.milkit.app.api.coupon.response;

import java.util.Date;

import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponlog.CouponLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DcUseApprResponse extends ApprResponse {


	@ApiModelProperty(value="할인권 금액할인율")
	private Float dcRate;
	
    @ApiModelProperty(value="할인된 금액 (사용요청금액 - 할인금액")
    private Long useAmt;
	
	@ApiModelProperty(value="할인금액")
	private Long dcAmt;
	
	
	public DcUseApprResponse() {
		super();
	}

	public DcUseApprResponse(CouponLog couponLog) {
		super(couponLog.getApprNO(), couponLog.getApprTime());
	}

	
}
