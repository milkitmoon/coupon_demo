package com.milkit.app.common.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.milkit.app.common.ErrorCodeEnum;


@SuppressWarnings("serial")
public class CouponServiceException extends ServiceException {


	public CouponServiceException() {
		this(ErrorCodeEnum.ServiceException.getCode());
	}
	
	public CouponServiceException(String code) {
		super(code);
	}
	
	public CouponServiceException(String code, String[] objs) {
		super(code, objs);
	}
	
	
    public CouponServiceException(String code, String message) {
    	super(code, message);
    }
    
	public CouponServiceException(String code, String message, String[] objs) {
		super( code, message, objs );
	}


}
