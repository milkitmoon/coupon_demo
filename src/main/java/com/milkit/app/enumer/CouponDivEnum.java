package com.milkit.app.enumer;

public enum CouponDivEnum {

	BILL_COUPON("10"), DC_COUPON("20"), EXCHANGE_COUPON("30");

    private String value;
    

    CouponDivEnum(String value) {
		this.value = value;
	}
    
	public String getValue() {		
		return value;
	}
}
