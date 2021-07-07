package com.milkit.app.config.init;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.milkit.app.domain.couponinfo.CouponInfo;
import com.milkit.app.domain.couponinfo.service.CouponInfoServiceImpl;
import com.milkit.app.domain.couponnoseq.CouponNoSeq;
import com.milkit.app.domain.couponnoseq.service.CouponNoSeqServiceImpl;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;
import com.milkit.app.enumer.OperateStatusEnum;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
    private CouponNoSeqServiceImpl couponNoSeqService;
	
	@Autowired
    private CouponInfoServiceImpl couponInfoService;



    public void run(ApplicationArguments args) {
	
		String couponCD = "000001";
		
		CouponInfo couponInfo = new CouponInfo();
		couponInfo.setSeq(1l);
		couponInfo.setCouponCD(couponCD);
		couponInfo.setCouponNM("1만원금액권쿠폰");
		couponInfo.setPubDiv("1");
		couponInfo.setCouponDiv("10");
		couponInfo.setFaceAmt(10000l);
		couponInfo.setPubAmt(9000l);
		couponInfo.setChangeYN("Y");
		couponInfo.setChangeMinUseRate(60.0f);
		couponInfo.setFeeRate(50.0f);
		couponInfo.setExpiryDiv("2");
		couponInfo.setExpiryDay(365);
		couponInfo.setDescription("테스트 금액쿠폰정보");
		
		couponInfo.setOperateStatus(OperateStatusEnum.CONFIRM.getValue());
		couponInfo.setUseYN("Y");
		couponInfo.setInstTime(new Date());
		couponInfo.setUpdTime(new Date());
		couponInfo.setInstUser("admin");
		couponInfo.setUpdUser("admin");

		CouponNoSeq couponNoSeq = new CouponNoSeq(couponCD, 0l);
		
		String couponCD2 = "000002";
		
		CouponInfo couponInfo2 = new CouponInfo();
		couponInfo2.setSeq(2l);
		couponInfo2.setCouponCD(couponCD2);
		couponInfo2.setCouponNM("10%할인쿠폰");
		couponInfo2.setPubDiv("1");
		couponInfo2.setCouponDiv("20");
		couponInfo2.setDcRate(10f);
		couponInfo2.setDcPossMinAmt(1000l);
		couponInfo2.setDcMaxAmt(10000l);
		couponInfo2.setFeeRate(50.0f);
		couponInfo2.setExpiryDiv("1");
		couponInfo2.setApprStartDT("20201001");
		couponInfo2.setApprEndDT("20901231");
		couponInfo2.setDescription("테스트 할인쿠폰정보");
		
		couponInfo2.setOperateStatus(OperateStatusEnum.CONFIRM.getValue());
		couponInfo2.setUseYN("Y");
		couponInfo2.setInstTime(new Date());
		couponInfo2.setUpdTime(new Date());
		couponInfo2.setInstUser("admin");
		couponInfo2.setUpdUser("admin");

		CouponNoSeq couponNoSeq2 = new CouponNoSeq(couponCD2, 0l);
		
		try {
			couponInfoService.insert(couponInfo);
			couponNoSeqService.insert(couponNoSeq);
			
			couponInfoService.insert(couponInfo2);
			couponNoSeqService.insert(couponNoSeq2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


}