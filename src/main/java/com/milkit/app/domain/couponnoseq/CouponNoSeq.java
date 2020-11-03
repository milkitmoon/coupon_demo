package com.milkit.app.domain.couponnoseq;

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
import com.milkit.app.domain.coupon.Coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Table(name = "COUPON_NO_SEQ")
@Entity
@ApiModel
public class CouponNoSeq {

	@Id
    @Column(name = "COUPON_CD")
    @ApiModelProperty(value="쿠폰코드")
    private String couponCD;
    
    @Column(name = "SEQ")
    @ApiModelProperty(value="COUPON_CRIT의 키ID")
    private Long seq;
    
    public CouponNoSeq() {}
    
    public CouponNoSeq(String couponCD, Long seq) {
    	this.couponCD = couponCD;
    	this.seq = seq;
    }

	
	@Override  
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.SHORT_PREFIX_STYLE
		);
	}
	
}
