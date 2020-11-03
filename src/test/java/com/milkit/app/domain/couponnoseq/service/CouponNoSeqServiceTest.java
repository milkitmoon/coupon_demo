package com.milkit.app.domain.couponnoseq.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponnoseq.CouponNoSeq;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;
import com.milkit.app.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@Slf4j
class CouponNoSeqServiceTest {

	@Autowired
    private CouponNoSeqServiceImpl couponNoSeqService;

	@BeforeEach
	@DisplayName("쿠폰번호순번정보를 등록한다.")
	private void insert_test() throws Exception {
		String couponCD = "000001";
		CouponNoSeq couponNoSeq = new CouponNoSeq(couponCD, 0l);
		couponNoSeqService.insert(couponNoSeq);
	}
	

	
	@Test
	@DisplayName("현재 쿠폰번호순번정보를 조회한다.")
	public void selectCurrCouponnoSeq_test() throws Exception {
		String couponCD = "000001";
		long result = couponNoSeqService.selectCurrCouponnoSeq(couponCD);
log.debug("result:"+result);		
		assertTrue(result == 0);
	}
	
	@Test
	@DisplayName("다음 쿠폰번호순번정보를정보를 조회한다.")
	public void selectNextCouponnoSeq_test() throws Exception {
		String couponCD = "000001";
		long result = couponNoSeqService.selectNextCouponnoSeq(couponCD);
log.debug("result:"+result);		
		assertTrue(result > 0);
	}

	
	@Test
	@DisplayName("다음 쿠폰번호순번정보를정보를 조회한다.")
	public void thread_test() throws Exception {
		String couponCD = "000001";
		
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).forEach( n -> 
        	executor.execute(() -> {
            	long result = -1l;
				try {
					result = couponNoSeqService.selectNextCouponnoSeq(couponCD);

				} catch (Exception e) {
					e.printStackTrace();
				}
                log.debug("	result:"+result);
                
                try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

            })
        );

	}
	
	@Test
	@DisplayName("다음 쿠폰번호순번정보를정보를 n회 조회한다.")
	public void temp_test() throws Exception {
		String couponCD = "000001";
		
        for(int i=0; i<10; i++) {
           	long result = couponNoSeqService.selectNextCouponnoSeq(couponCD);
            log.debug("	result:"+result);
            TimeUnit.MILLISECONDS.sleep(1);
        }

	}


}
