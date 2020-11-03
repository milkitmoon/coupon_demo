package com.milkit.app.api.coupon;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkit.app.api.coupon.request.ApprRequest;
import com.milkit.app.api.coupon.request.CancelApprRequest;
import com.milkit.app.api.coupon.request.UseApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.api.userinfo.UserInfoController;
import com.milkit.app.common.response.GenericResponse;
import com.milkit.app.config.WebSecurityConfigure;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.couponlog.CouponLog;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;

import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootTest
@AutoConfigureMockMvc
@Import(WebSecurityConfigure.class)
@Slf4j
public class CouponPublishControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	  
    
    @Test
    @DisplayName("신규쿠폰을 발행하여  전달한다.")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void publish_test() throws Exception {
		Coupon coupon = Coupon
		.builder()
		.couponCD("000001")
		.userID("milkit")
		.build();
		
//    	String content = objectMapper.writeValueAsString(coupon);
    	String content = "{\"couponCD\":\"000001\",\"userID\":\"milkit\"}";
    	
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/coupon/publish").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    	        .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("0"))
                .andExpect(jsonPath("message").value("성공했습니다"))
                .andExpect(jsonPath("value.couponCD").value("000001"))
                .andExpect(jsonPath("value.couponNO").isNotEmpty())
    			;
    }
    
    @Test
    @DisplayName("발행할 쿠폰 정보가 없는 오류를 확인한다.")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void publish_exception1_test() throws Exception {
    	String content = "{\"couponCD\":\"000003\",\"userID\":\"milkit\"}";
    	
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/coupon/publish").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    	        .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("601"))
                .andExpect(jsonPath("message").value("쿠폰기본정보가 존재하지 않습니다. 요청쿠폰코드:000003"))
                .andExpect(jsonPath("value").isEmpty())
    			;
    }
    

    
}
