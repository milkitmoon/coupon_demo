package com.milkit.app.api.coupon.response;

import java.util.Date;

import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponlog.CouponLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BillUseApprResponse extends ApprResponse {


	@ApiModelProperty(value="상품권 액면금액")
	private Long faceAmt;
	
	@ApiModelProperty(value="사용금액 (실제 승인금액)")
	private Long useAmt;
	
	@ApiModelProperty(value="거스름돈 금액")
	private Long changeAmt;
	
	
	public BillUseApprResponse() {
		super();
	}

	public BillUseApprResponse(CouponLog couponLog) {
		super(couponLog.getApprNO(), couponLog.getApprTime());
	}

	
}
