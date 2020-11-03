package com.milkit.app.domain.userinfo;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.milkit.app.config.jwt.JwtDetails;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "USER_INFO")
@Entity
@ApiModel
public class UserInfo implements UserDetails, JwtDetails {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="UserInfo의 키ID")
    private Long id;
    
    @Column(name = "USER_ID")
    @ApiModelProperty(value="사용자계정 ID")
    private String userID;
    
    @Column(name = "PASSWORD")
    @ApiModelProperty(value="사용자계정 비밀번호")
    private String password;

    @Column(name = "USER_NM")
    @ApiModelProperty(value="사용자명")
    private String userNM;

    @Column(name = "AUTH_ROLE")
    @ApiModelProperty(value="사용자 권한", notes="(ROLE_MEMBER:사용자, ROLE_ADMIN:관리자)")
    private String authRole;
    
    @Column(name = "USE_YN")
    @ApiModelProperty(value="사용자 삭제여부")
    private String useYN;
    
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


    public UserInfo() {}
    public UserInfo(String userID, String password) {
        this.userID = userID;
        this.password = password;
        this.authRole = RoleEnum.MEMBER.getValue();
    }
    
    public UserInfo(Long id, String userID, String authRole) {
    	this.id = id;
        this.userID = userID;
        this.authRole = authRole;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserNM() {
		return userNM;
	}
	public void setUserNM(String userNM) {
		this.userNM = userNM;
	}
	public String getAuthRole() {
		return authRole;
	}
	public void setAuthRole(String authRole) {
		this.authRole = authRole;
	}
	public String getUseYN() {
		return useYN;
	}
	public void setUseYN(String useYN) {
		this.useYN = useYN;
	}
	public Date getInstTime() {
		return instTime;
	}
	public void setInstTime(Date instTime) {
		this.instTime = instTime;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	public String getInstUser() {
		return instUser;
	}
	public void setInstUser(String instUser) {
		this.instUser = instUser;
	}
	public String getUpdUser() {
		return updUser;
	}
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return userID;
	}
	public void setUsername(String userID) {
		this.userID = userID;
	}
	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isEnabled() {
		boolean isEnabled = false;
		
		if(useYN != null && useYN.equals("Y")) {
			isEnabled = true;
		}
		return isEnabled;
	}
	
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(authRole.equals(RoleEnum.ADMIN.getValue())) {
        	grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.ADMIN.getValue()));
        } else {
        	grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.MEMBER.getValue()));
        }
        
		return grantedAuthorities;
	}
	
	public static Collection<? extends GrantedAuthority> getAuthorities(String authority) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
       	grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        
		return grantedAuthorities;
	}
	
	@Override  
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.SHORT_PREFIX_STYLE
		);
	}
	
	@Override
	@JsonIgnore
	public String getSubject() {
		return userID;
	}
	@Override
	@JsonIgnore
	public Map<String, Object> getClaims() {
        Map<String, Object> claims = new HashMap<>();

        claims.put("name", getUserID());
        claims.put("userNM", getUserNM());
        claims.put("authRole", getAuthRole());
        
        return claims;
	}
    
}