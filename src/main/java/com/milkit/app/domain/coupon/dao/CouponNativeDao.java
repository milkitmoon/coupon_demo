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
	public Coupon findPreRegistCoupon(@Param("couponCD") String couponNO);
	

/*	
	@Query(value = 
			"SELECT \r\n" + 
			"	bl.YEAR as year, b.BANK_CD as bankCD, b.BANK_NM as bankNM, bl.AMOUNT as amount \r\n" +
			"FROM BANK b\r\n" + 
			"INNER JOIN (\r\n" + 
			"	SELECT \r\n" + 
			"		YEAR, BANK_CD, sum(AMOUNT) AS AMOUNT \r\n" + 
			"	FROM BANK_LOAN \r\n" + 
			"	GROUP BY YEAR, BANK_CD\r\n" + 
			") bl\r\n" + 
			"ON b.BANK_CD = bl.BANK_CD\r\n" + 
			"ORDER BY bl.YEAR, b.BANK_CD", nativeQuery = true)
	public List<YearBankLoan> findYearBankLoan();
	

	public default Map<String, Long> findYearBankLoanTotalMap() {
        return findYearBankLoanTotal().stream().collect(Collectors.toMap(YearBankLoanTotal::getYear, YearBankLoanTotal::getTotalAmount));
    }	
	@Query(value = 
			"SELECT \r\n" + 
			"	YEAR, sum(AMOUNT) AS totalAmount \r\n" + 
			"FROM BANK_LOAN \r\n" + 
			"GROUP BY YEAR\r\n" + 
			"ORDER BY \"YEAR\" ", nativeQuery = true)
	public List<YearBankLoanTotal> findYearBankLoanTotal();
	interface YearBankLoanTotal {
		public String getYear();
		public long getTotalAmount();
	}
	
	
	@Query(value = 
			"SELECT t1.*, (SELECT BANK_NM FROM BANK b WHERE b.BANK_CD = t1.BANK_CD) AS bankNM\r\n" + 
			"	FROM (\r\n" + 
			"		SELECT YEAR, BANK_CD, SUM(AMOUNT) AS AMOUNT\r\n" + 
			"		FROM BANK_LOAN\r\n" + 
			"		GROUP BY YEAR, BANK_CD\r\n" + 
			"	) t1\r\n" + 
			"	WHERE AMOUNT = (SELECT MAX(AMOUNT) FROM ( SELECT SUM(AMOUNT) AS AMOUNT FROM BANK_LOAN GROUP BY YEAR, BANK_CD ))", nativeQuery = true)
	public MaxBankLoan findMaxBankLoan();
	
	
	@Query(value = 
			"SELECT t1.YEAR, t1.BANK_CD as bankCD, ROUND(t1.AMOUNT) AS AMOUNT\r\n" + 
			"FROM \r\n" + 
			"	(\r\n" + 
			"		SELECT YEAR, BANK_CD, SUM(AMOUNT)/12 AS AMOUNT \r\n" + 
			"		FROM BANK_LOAN\r\n" + 
			"		WHERE BANK_CD = (SELECT BANK_CD FROM BANK WHERE BANK_NM = :bankNM)\r\n" + 
			"		GROUP BY YEAR, BANK_CD\r\n" + 
			"	)t1\r\n" + 
			"INNER JOIN\r\n" + 
			"(\r\n" + 
			"	SELECT MIN(AMOUNT) AS MIN_AMOUNT, MAX(AMOUNT) AS MAX_AMOUNT FROM (\r\n" + 
			"		SELECT SUM(AMOUNT)/12 AS AMOUNT \r\n" + 
			"		FROM BANK_LOAN \r\n" + 
			"		WHERE BANK_CD = (SELECT BANK_CD FROM BANK WHERE BANK_NM = :bankNM)\r\n" + 
			"		GROUP BY YEAR, BANK_CD\r\n" + 
			"	)\r\n" + 
			") t2\r\n" + 
			"ON t1.AMOUNT = t2.MIN_AMOUNT OR t1.AMOUNT = t2.MAX_AMOUNT", nativeQuery = true)
	public List<MinMaxBankLoan> findMinMaxBankLoan(@Param("bankNM") String bankNM);


	@Query(value = 
			"SELECT YEAR , MONTH , sum(AMOUNT) AS AMOUNT FROM BANK_LOAN \r\n" + 
			"WHERE BANK_CD = (SELECT BANK_CD FROM BANK WHERE BANK_NM = :bankNM) \r\n" + 
			"AND MONTH = :month \r\n" + 
			"GROUP BY YEAR, MONTH \r\n" + 
			"ORDER BY YEAR desc \r\n" + 
			"LIMIT :limitValue", nativeQuery = true)
	public List<RecentBankLoan> findRecentBankLoan(@Param("bankNM") String bankNM, @Param("month") String month, @Param("limitValue") int limitValue);
	
*/
	
}
