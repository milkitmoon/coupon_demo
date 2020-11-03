package com.milkit.app.domain.couponnoseq.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.milkit.app.domain.couponnoseq.CouponNoSeq;
import com.milkit.app.domain.couponnoseq.dao.CouponNoSeqNativeDao;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CouponNoSeqServiceImpl {
	
    @Autowired
    private CouponNoSeqNativeDao couponNoSeqNativeDao;
    
    public String insert(CouponNoSeq couponNoSeq) throws Exception {
        return couponNoSeqNativeDao.save(couponNoSeq).getCouponCD();
    }
    
	public Long selectCurrCouponnoSeq(String couponCD) throws Exception {
		return couponNoSeqNativeDao.findCurrCouponnoSeq(couponCD);
	}

	public synchronized Long selectNextCouponnoSeq(String couponCD) throws Exception {
		updateNextCouponnoSeq(couponCD);
		return selectCurrCouponnoSeq(couponCD);
	}
	
	protected Integer updateNextCouponnoSeq(String couponCD) throws Exception {
		return couponNoSeqNativeDao.updateNextCouponnoSeq(couponCD);
	}

}
