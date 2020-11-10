package com.milkit.app.domain.coupon.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.milkit.app.domain.coupon.Coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CouponNativeDao extends JpaRepository<Coupon, Long>  {
	
	@Transactional
	@Modifying
	@Query(value=
		"INSERT INTO COUPON ( \r\n" + 
		"	COUPON_CD, COUPON_NO, COUPON_SEQ, \r\n" + 
		"	USER_ID, REGIST_DT, PUB_DT, APPR_START_DT, APPR_END_DT, PUB_TIME, \r\n" + 
		"	FACE_AMT, PUB_AMT, DC_RATE, STATUS, \r\n" + 
		"	USE_YN, INST_TIME, UPD_TIME, INST_USER, UPD_USER \r\n" + 
		") VALUES ( \r\n" + 
		"	:#{#coupon.couponCD}, :#{#coupon.couponNO},  (SELECT IFNULL(MAX(DISTINCT COUPON_SEQ), 0)+1 AS COUPON_SEQ FROM COUPON c WHERE COUPON_CD = :#{#coupon.couponCD}), \r\n" + 
		"	:#{#coupon.userID}, :#{#coupon.registDT}, :#{#coupon.pubDT}, :#{#coupon.apprStartDT}, :#{#coupon.apprEndDT}, :#{#coupon.pubTime}, \r\n" + 
		"	:#{#coupon.faceAmt}, :#{#coupon.pubAmt}, :#{#coupon.dcRate}, :#{#coupon.status}, \r\n" + 
		"	:#{#coupon.useYN}, SYSDATE, SYSDATE, :#{#coupon.instUser}, :#{#coupon.updUser} \r\n" + 
		") ", 
	nativeQuery=true)
	public Integer insert(@Param("coupon") Coupon coupon);

	@Query(value = 
			"SELECT count(SEQ) AS MAX_PUB_QTY FROM COUPON c \r\n" + 
			"WHERE COUPON_CD = :couponCD \r\n" + 
			"AND STATUS != '1'", nativeQuery = true)
	public int findPublishedCouponQty(@Param("couponCD") String couponCD);

	@Query(value = 
			"SELECT * FROM COUPON c \r\n" + 
			"WHERE COUPON_SEQ  = ( \r\n" + 
			"	SELECT IFNULL(MAX(DISTINCT COUPON_SEQ), -1) AS COUPON_SEQ FROM COUPON c WHERE COUPON_CD = :couponCD AND STATUS = '1' AND USE_YN = 'Y' \r\n" + 
			")"
			, nativeQuery = true)
	public Optional<Coupon> findPreRegistCoupon(@Param("couponCD") String couponNO);
	

}
