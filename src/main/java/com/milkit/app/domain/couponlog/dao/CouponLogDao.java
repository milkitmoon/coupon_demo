package com.milkit.app.domain.couponlog.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.userinfo.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CouponLogDao extends JpaRepository<CouponLog, Long>  {

	public List<CouponLog> findByCouponNO(String couponNO) throws Exception;

	public CouponLog findByApprNO(String apprNO) throws Exception;

}
