package com.milkit.app.domain.couponlog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.dao.CouponDao;
import com.milkit.app.domain.coupon.dao.CouponNativeDao;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponinfo.dao.CouponInfoDao;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.couponlog.dao.CouponLogDao;
import com.milkit.app.domain.couponnoseq.CouponNoSeq;
import com.milkit.app.domain.couponnoseq.dao.CouponNoSeqNativeDao;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CouponLogServiceImpl {
	
    @Autowired
    private CouponLogDao couponLogDao;
    

    
    public CouponLog insert(CouponLog couponLog) throws Exception {
        return couponLogDao.save(couponLog);
    }



	public CouponLog getCouponLogByApprNO(String apprNO) throws Exception {
		return couponLogDao.findByApprNO(apprNO);
	}




}
