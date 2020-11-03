package com.milkit.app.domain.couponinfo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponinfo.dao.CouponInfoDao;
import com.milkit.app.domain.couponnoseq.CouponNoSeq;
import com.milkit.app.domain.couponnoseq.dao.CouponNoSeqNativeDao;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CouponInfoServiceImpl {
	
    @Autowired
    private CouponInfoDao couponInfoDao;
    
    public Long insert(CouponInfo couponInfo) throws Exception {
        return couponInfoDao.save(couponInfo).getSeq();
    }

	public CouponInfo getCouponInfo(String couponCD) throws Exception {
		return couponInfoDao.findByCouponCD(couponCD);
	}


}
