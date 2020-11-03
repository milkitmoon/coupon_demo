package com.milkit.app.domain.couponlog.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.milkit.app.common.CouponSizeCommon;
import com.milkit.app.util.StringUtil;


@Component
public class ApprNoGeneratorServiceImpl {

	
	public String generateApprNo(Date apprTime, long seq) {
//		String apprNO = prefixString(Long.toString(seq), Long.toString(apprTime.getTime()), "0", MessageCommon.getInstance().GIFT_LOG_APPR_NO_SIZE);
		
		String apprNO = prefixString(Long.toString(apprTime.getTime()), Long.toString(seq), "0", CouponSizeCommon.COUPON_LOG_APPR_NO_SIZE);
		
//		String apprNO = prefixString("15312341566484848988", Long.toString(apprTime.getTime()), "0", MessageCommon.getInstance().POINT_LOG_APPR_NO_SIZE);
		
		return apprNO;
	}
	
	
    public String prefixString(String srcStr, String prefixStr, String paddingStr, int size) {
    	
    	String apprNO = "";
    	
    	int totalSize = 0;
    	
    	if(prefixStr != null) {
    		totalSize = (size - (srcStr.length() + prefixStr.length()));
    		
    		size = (size - prefixStr.length());
    		
    		if(totalSize < 0) {
    			int subStrSize = prefixStr.length() - (totalSize*-1);
    			prefixStr = prefixStr.substring(0, subStrSize);
    		}
    		
    		apprNO =  StringUtil.reverse(prefixStr) + StringUtil.leftPad(srcStr, paddingStr, size);
    	} else {
    		apprNO = StringUtil.reverse( StringUtil.leftPad(srcStr, paddingStr, size) );
    	}
    	
//    	return StringUtils.reverse(apprNO);
    	return apprNO;
    	
    }

}
