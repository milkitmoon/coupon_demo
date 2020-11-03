package com.milkit.app.domain.couponinfo;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.milkit.app.domain.coupon.Coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Table(name = "COUPON_INFO")
@Entity
@ApiModel
public class CouponInfo implements Serializable {

    @Id
    @Column(name = "SEQ")
    @ApiModelProperty(value="COUPON_INFO의 키ID")
    private Long seq;
    
    @Column(name = "COUPON_CD", unique=true)
    @ApiModelProperty(value="쿠폰마스터코드")
    private String couponCD;
    
    @Column(name = "COUPON_NM")
    @ApiModelProperty(value="쿠폰명")
    private String couponNM;
    
    @Column(name = "PUB_DIV")
    @ApiModelProperty(value="쿠폰발행구분")
    private String pubDiv;
    
    @Column(name = "COUPON_DIV")
    @ApiModelProperty(value="쿠폰구분")
    private String couponDiv;
    
    @Column(name = "MAX_PUB_QTY")
    @ApiModelProperty(value="최대발행쿠폰수량")
    private int maxPubQty;
    
    @Column(name = "FACE_AMT")
    @ApiModelProperty(value="액면금액")
    private Long faceAmt;
    
    @Column(name = "PUB_AMT")
    @ApiModelProperty(value="발행금액")
    private Long pubAmt;
    
    @Column(name = "DC_RATE")
    @ApiModelProperty(value="할인권 금액할인율")
    private float dcRate;
    
    @Column(name = "DC_POSS_MIN_AMT")
    @ApiModelProperty(value="할인권 최소사용금액")
    private Long dcPossMinAmt;
    
    
    @Column(name = "DC_MAX_AMT")
    @ApiModelProperty(value="할인권 최대할인금액")
    private Long dcMaxAmt;
    
    @Column(name = "CHANGE_YN")
    @ApiModelProperty(value="거스름돈여부")
    private String changeYN;
    
    @Column(name = "CHANGE_MIN_USE_RATE")
    @ApiModelProperty(value="거스름돈지급금액비율")
    private float changeMinUseRate;

    @Column(name = "FEE_RATE")
    @ApiModelProperty(value="수수료율")
    private float feeRate;
    
    @Column(name = "EXPIRY_DIV")
    @ApiModelProperty(value="유효기간구분")
    private String expiryDiv;
    
    @Column(name = "APPR_START_DT")
    @ApiModelProperty(value="승인시작일자")
    private String apprStartDT;
    
    @Column(name = "APPR_END_DT")
    @ApiModelProperty(value="승인종료일자")
    private String apprEndDT;
    
    @Column(name = "EXPIRY_DAY")
    @ApiModelProperty(value="유효기간")
    private int expiryDay;
    
    @Column(name = "DESCRIPTION")
    @ApiModelProperty(value="상세설명")
    private String description;
    
    @Column(name = "OPERATE_STATUS")
    @ApiModelProperty(value="운영상태")
    private String operateStatus;

    @Column(name = "USE_YN")
    @ApiModelProperty(value="사용여부 (Y:사용,N:미사용)")
    private String useYN;

    @Column(name = "INST_TIME")
    @ApiModelProperty(value="등록시간")
    private Date instTime;

    @Column(name = "UPD_TIME")
    @ApiModelProperty(value="갱신시간")
    private Date updTime;

    @Column(name = "INST_USER")
    @ApiModelProperty(value="글작성자")
    private String instUser;

    @Column(name = "UPD_USER")
    @ApiModelProperty(value="글수정자")
    private String updUser;
  
	
	@Override  
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.SHORT_PREFIX_STYLE
		);
	}
	
}
