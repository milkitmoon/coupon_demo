package com.milkit.app.domain.couponnoseq.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponnoseq.CouponNoSeq;
import com.milkit.app.domain.userinfo.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CouponNoSeqNativeDao extends JpaRepository<CouponNoSeq, String>  {
 
	@Transactional
	@Modifying
	@Query(value = 
			"UPDATE COUPON_NO_SEQ SET SEQ=SEQ+1 WHERE COUPON_CD = :couponCD" , nativeQuery = true)
	public Integer updateNextCouponnoSeq(@Param("couponCD") String couponCD);
	

	@Query(value = 
			"SELECT SEQ FROM COUPON_NO_SEQ WHERE COUPON_CD = :couponCD limit 1;", nativeQuery = true)
	public Long findCurrCouponnoSeq(@Param("couponCD") String couponCD);
	

}
