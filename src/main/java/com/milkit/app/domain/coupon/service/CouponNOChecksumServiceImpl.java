package com.milkit.app.domain.coupon.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.milkit.app.common.CouponSizeCommon;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.dao.CouponDao;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponinfo.dao.CouponInfoDao;
import com.milkit.app.domain.couponnoseq.CouponNoSeq;
import com.milkit.app.domain.couponnoseq.dao.CouponNoSeqNativeDao;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CouponNOChecksumServiceImpl {
	
    
    public String getChecksum(String source) {
		int[] sourceArr = Arrays.stream(source.split("")).mapToInt(Integer::parseInt).toArray();
		
        int sum = 0;
        int[] key = {4, 2, 6, 9, 5, 1, 3, 7, 9, 1, 1, 8, 3, 7, 1, 3, 5};

        for (int i = 0 ; i < CouponSizeCommon.COUPON_CHECKSUM_EXCEPT_SIZE ; i++) { 
        	sum += (key[i] * sourceArr[i]); 
        }

        int chkSum = (int) Math.floor( key[CouponSizeCommon.COUPON_CHECKSUM_EXCEPT_SIZE-1]*sourceArr[CouponSizeCommon.COUPON_CHECKSUM_EXCEPT_SIZE-1] / 10 );
        sum +=chkSum;
        
        return String.valueOf(sum);
    }

	public String getTotalChecksum(String source) {
		return source+getChecksum(source);
	}

	public boolean checksum(String source) {
		boolean checksum = false;
		
		if(source != null && source.length() == CouponSizeCommon.COUPON_NO_SIZE) {
			String targetStr = source.substring(0, CouponSizeCommon.COUPON_CHECKSUM_EXCEPT_SIZE);
			String checksumStr = source.substring(CouponSizeCommon.COUPON_CHECKSUM_EXCEPT_SIZE, CouponSizeCommon.COUPON_NO_SIZE);
			
//log.debug("targetStr:"+targetStr);
//log.debug("checksumStr:"+checksumStr);
			
			String expectChecksum = getChecksum(targetStr);
			
//log.debug("expectChecksum:"+expectChecksum);
			
			if(checksumStr.equals(expectChecksum)) {
				checksum = true;
			}
		}
		
		return checksum;
	}

}
