package com.milkit.app.domain.coupon.service;

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
import com.milkit.app.domain.couponlog.service.CouponLogServiceImpl;
import com.milkit.app.domain.couponnoseq.CouponNoSeq;
import com.milkit.app.domain.couponnoseq.dao.CouponNoSeqNativeDao;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CouponServiceImpl {
	
    @Autowired
    private CouponDao couponDao;
    
    @Autowired
    private CouponNativeDao couponNativeDao;
    
	@Autowired
	private CouponLogServiceImpl couponLogService;
    
    
    public Integer insert(Coupon coupon) throws Exception {
        return couponNativeDao.insert(coupon);
    }

	public Coupon getCoupon(String couponNO) throws Exception {
		return couponDao.findByCouponNO(couponNO);
	}

	public int getPublishedCouponQty(String couponCD) throws Exception {
		return couponNativeDao.findPublishedCouponQty(couponCD);
	}
	
	public Coupon getPreRegistCoupon(String couponNO) throws Exception {
		return couponNativeDao.findPreRegistCoupon(couponNO);
	}

	public Coupon publishPreRegistCoupon(Coupon coupon) throws Exception {
		return couponDao.save(coupon);
	}

	public Coupon update(Coupon coupon) throws Exception {
		return couponDao.save(coupon);
	}

	@Transactional
	public Coupon updateReturn(Coupon coupon, CouponLog cxlCouponLog) throws Exception {
		Coupon rtnCoupon = update(coupon);
		CouponLog rtnCouponLog = couponLogService.insert(cxlCouponLog);
log.debug("rtnCoupon:"+rtnCoupon.toString());
log.debug("rtnCouponLog:"+rtnCouponLog.toString());
		
		return rtnCoupon;
	}


}
