package com.milkit.app.domain.couponinfo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;
import com.milkit.app.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Slf4j
class CouponInfoServiceTest {


	@Autowired
    private CouponInfoServiceImpl couponInfoService;
	
	
	@Test
	@DisplayName("쿠폰코드에 따른 쿠폰기본정보를 조회한다.")
	public void getCouponInfo_test() throws Exception {
		String couponCD = "000001";
		CouponInfo result = couponInfoService.getCouponInfo(couponCD);
log.debug(StringUtil.toJsonString(result));
		assertTrue(result.getCouponCD().equals(couponCD));
	}
	
	

/*	
	@Test
	@DisplayName("지원금정보를 등록한다.")
	public void insert_test() throws Exception {
		BankLoan bankLoan = new BankLoan("2000", "1", "111111", 10000l);
		Long id = bankLoanService.insert(bankLoan);
		
		assertTrue(id > 1L);
	}
	
	@Test
	@DisplayName("복수의 지원금정보를 등록한다.")
	public void insertList_test() throws Exception {
		List<BankLoan> list = new ArrayList<BankLoan>();
		BankLoan bankLoan1 = new BankLoan("2000", "1", "222222", 10000l);
		BankLoan bankLoan2 = new BankLoan("2000", "2", "333333", 20000l);
		
		list.add(bankLoan1);
		list.add(bankLoan2);
		
		int count = bankLoanService.insert(list);
		
		assertTrue(count == 2);
	}
	
	@Test
	@DisplayName("전체 지원금정보를 조회한다.")
	public void selectAll_test() throws Exception {
		List<BankLoan> list = bankLoanService.selectAll();
log.debug(list.toString());		
		assertTrue(list.size() > 0);
	}
	
	@Test
	@DisplayName("년도별 은행의 지원금 정보를 조회한다.")
	public void selectYearBankLoan_test() throws Exception {
		List<YearBankLoan> list = bankLoanService.selectYearBankLoan();
log.debug(StringUtil.toJsonString(list));
		assertTrue(list.size() > 0);
	}
	
	@Test
	@DisplayName("년도별 총 지원금 정보를 조회한다.")
	public void selectBankLoanTotal_test() throws Exception {
		Map<String, Long> result = bankLoanService.selectYearBankLoanTotalMap();
log.debug(StringUtil.toJsonString(result));
		assertTrue(result.size() > 0);
	}
	
	@Test
	@DisplayName("특정년도의 가장 많은 지원금을 전달한 은행을 조회한다.")
	public void selectMaxBankLoan_test() throws Exception {
		MaxBankLoan result = bankLoanService.selectMaxBankLoan();
log.debug(StringUtil.toJsonString(result));
		assertTrue(result != null);
	}
	
	@Test
	@DisplayName("은행별로 가장 적은 지원금과 가장 많은 지원금을 전달한 정보를 조회한다.")
	public void selectMinMaxBankLoan_test() throws Exception {
		String bankNM = "외환은행";
		List<MinMaxBankLoan> result = bankLoanService.selectMinMaxBankLoan(bankNM);
log.debug(StringUtil.toJsonString(result));
		assertTrue(result != null);
	}
	
	@Test
	@DisplayName("특정은행 특정달의 최근 지원금  정보를 조회한다.")
	public void selectLastestBankLoan_test() throws Exception {
//		String bankNM = "주택도시기금";
		String bankNM = "외환은행";
		String month = "9";
		int limitValue = 4;
		List<RecentBankLoan> result = bankLoanService.selectRecentBankLoan(bankNM, month, limitValue);
log.debug(StringUtil.toJsonString(result));
		assertTrue(result != null);
	}
*/

}
