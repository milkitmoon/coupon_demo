package com.milkit.app.domain.coupon;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.milkit.app.domain.couponinfo.CouponInfo;

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
@Table(name = "COUPON")
@Entity
@ApiModel
public class Coupon implements Serializable {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="Coupon의 키ID")
    @JsonIgnore
    @Column(name = "SEQ")
    private Long seq;
    
    @Column(name = "COUPON_CD")
    @ApiModelProperty(value="쿠폰코드")
    private String couponCD;
    
    @Column(name = "COUPON_NO", unique=true)
    @ApiModelProperty(value="쿠폰번호")
    private String couponNO;
    
    @Column(name = "COUPON_SEQ")
    @ApiModelProperty(value="쿠등록순번")
    @JsonIgnore
	private int couponSeq;
    
    @Column(name = "USER_ID")
    @ApiModelProperty(value="쿠폰발급계정")
    private String userID;
    
    @Column(name = "REGIST_DT")
    @ApiModelProperty(value="등록일자")
    @JsonIgnore
    private String registDT;
    
    @Column(name = "PUB_DT")
    @ApiModelProperty(value="발행일자")
    private String pubDT;
    
    @Column(name = "APPR_START_DT")
    @ApiModelProperty(value="승인시작일자")
    private String apprStartDT;
    
    @Column(name = "APPR_END_DT")
    @ApiModelProperty(value="유효일자")
    private String apprEndDT;
    
    @Column(name = "PUB_TIME")
    @ApiModelProperty(value="발행시간")
    private Date pubTime;
    
    @Column(name = "DISCARD_TIME")
    @ApiModelProperty(value="폐기시간")
    @JsonIgnore
    private Date discardTime;
    
    @Column(name = "USE_TIME")
    @ApiModelProperty(value="사용시간")
    private Date useTime;
    
    @Column(name = "FACE_AMT")
    @ApiModelProperty(value="액면금액")
    private Long faceAmt;
    
    @Column(name = "PUB_AMT")
    @ApiModelProperty(value="발행금액")
    @JsonIgnore
    private Long pubAmt;
    
    @Column(name = "USE_AMT")
    @ApiModelProperty(value="사용금액")
    @JsonIgnore
    private Long useAmt;
    
    @Column(name = "DC_RATE")
    @ApiModelProperty(value="할인율")
    private float dcRate;
    
    @Column(name = "STATUS")
    @ApiModelProperty(value="쿠폰상태")
    private String status;
    
    @Column(name = "USE_YN")
    @ApiModelProperty(value="사용자 삭제여부")
    private String useYN;
    
    @Column(name = "INST_TIME")
    @ApiModelProperty(value="사용자 등록시간")
    @JsonIgnore
    private Date instTime;
    
    @Column(name = "UPD_TIME")
    @ApiModelProperty(value="사용자 갱신시간")
    @JsonIgnore
    private Date updTime;
    
    @Column(name = "INST_USER")
    @ApiModelProperty(value="사용자 등록자")
    @JsonIgnore
    private String instUser;
    
    @Column(name = "UPD_USER")
    @ApiModelProperty(value="사용자 갱신자")
    @JsonIgnore
    private String updUser;
    
    @ManyToOne
    @JoinColumn(name = "COUPON_CD", updatable = false, insertable = false, nullable=false)
    @JsonBackReference
    private CouponInfo couponInfo;
    

    @ApiModelProperty(value="쿠폰명")
    @Transient
    private String couponNM;
    
    @ApiModelProperty(value="쿠폰구분")
    @Transient
    private String couponDiv;
    

	@Override  
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.SHORT_PREFIX_STYLE
		);
	}

}
