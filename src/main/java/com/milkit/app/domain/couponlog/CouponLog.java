package com.milkit.app.domain.couponlog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.milkit.app.common.ErrorCodeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "COUPON_LOG")
@Entity
@ApiModel
public class CouponLog {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="CouponLog의 키ID")
    @Column(name = "SEQ")
    private Long seq;
    
    @Column(name = "SALE_DT")
    @ApiModelProperty(value="거래일자")
    private String saleDT;

    @Column(name = "COUPON_NO")
    @ApiModelProperty(value="쿠폰번호")
    private String couponNO;
    
    @Column(name = "USER_ID")
    @ApiModelProperty(value="쿠폰발급계정")
    private String userID;
    
    @Column(name = "PRODUCT_CD")
    @ApiModelProperty(value="교환상품코드")
    private String productCD;
    
    @Column(name = "TRADE_TYPE")
    @ApiModelProperty(value="거래유형(1:정상거래, 2:취소거래)")
    private String tradeType;
    
    @Column(name = "TRADE_DIV")
    @ApiModelProperty(value="거래구분")
    private String tradeDiv;
    
    @Column(name = "APPR_NO")
    @ApiModelProperty(value="승인인번호")
    private String apprNO;
    
    @Column(name = "CXL_APPR_NO")
    @ApiModelProperty(value="취소승인번호")
    private String cxlApprNO;
    
    @Column(name = "APPR_TIME")
    @ApiModelProperty(value="승인시간")
    private Date apprTime;
    
    @Column(name = "FACE_AMT")
    @ApiModelProperty(value="액면금액")
    private Long faceAmt;
    
    @Column(name = "PUB_AMT")
    @ApiModelProperty(value="발행금액")
    private Long pubAmt;
    
    @Column(name = "REQ_USE_AMT")
    @ApiModelProperty(value="사용요청금액")
    private Long reqUseAmt;
    
    @Column(name = "USE_AMT")
    @ApiModelProperty(value="사용금액")
    private Long useAmt;
    
    @Column(name = "CHANGE_AMT")
    @ApiModelProperty(value="거스름돈금액")
    private Long changeAmt;
    
    @Column(name = "DC_AMT")
    @ApiModelProperty(value="할인금액")
    private Long dcAmt;
    
    @Column(name = "RESULT_CD")
    @ApiModelProperty(value="결과코드")
    private String resultCD;
    
    
    @Column(name = "INST_TIME")
    @ApiModelProperty(value="사용자 등록시간")
    private Date instTime;
    
    @Column(name = "UPD_TIME")
    @ApiModelProperty(value="사용자 갱신시간")
    private Date updTime;
    
    @Column(name = "INST_USER")
    @ApiModelProperty(value="사용자 등록자")
    private String instUser;
    
    @Column(name = "UPD_USER")
    @ApiModelProperty(value="사용자 갱신자")
    private String updUser;
    
    

    
    
	@Override  
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.SHORT_PREFIX_STYLE
		);
	}

}
