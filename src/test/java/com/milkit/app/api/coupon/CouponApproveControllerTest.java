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
public class CouponApproveControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;

 
    @Test
    @DisplayName("금액쿠폰을 승인한다.")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void billcoupon_approve_test() throws Exception {
    	String publishContent = "{\"couponCD\":\"000001\",\"userID\":\"milkit\"}";
    	
    	MvcResult publishResult = mvc.perform(MockMvcRequestBuilders.post("/api/coupon/publish").content(publishContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
    	String json = publishResult.getResponse().getContentAsString();
    	GenericResponse<Coupon> coupon = objectMapper.readValue(json, new TypeReference<GenericResponse<Coupon>>() {});
    	String couponNO = coupon.getValue().getCouponNO();
    	
    	UseApprRequest useApprRequest = new UseApprRequest("milkit", couponNO, 9000l);
    	String content = objectMapper.writeValueAsString(useApprRequest);
    	
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/api/coupon/use").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    	        .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("0"))
                .andExpect(jsonPath("message").value("성공했습니다"))
                .andExpect(jsonPath("value.useAmt").value(9000))
                .andExpect(jsonPath("value.changeAmt").value(1000))
                .andExpect(jsonPath("value.apprNO").isNotEmpty())
    			;
    }
    
    @Test
    @DisplayName("할인쿠폰을 승인한다.")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void dccoupon_approve_test() throws Exception {
    	String publishContent = "{\"couponCD\":\"000002\",\"userID\":\"milkit\"}";
    	
    	MvcResult publishResult = mvc.perform(MockMvcRequestBuilders.post("/api/coupon/publish").content(publishContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
    	String json = publishResult.getResponse().getContentAsString();
    	GenericResponse<Coupon> coupon = objectMapper.readValue(json, new TypeReference<GenericResponse<Coupon>>() {});
    	String couponNO = coupon.getValue().getCouponNO();
    	
    	UseApprRequest useApprRequest = new UseApprRequest("milkit", couponNO, 9000l);
    	String content = objectMapper.writeValueAsString(useApprRequest);
    	
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/api/coupon/use").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    	        .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("0"))
                .andExpect(jsonPath("message").value("성공했습니다"))
                .andExpect(jsonPath("value.dcRate").value(10f))
                .andExpect(jsonPath("value.useAmt").value(8100))
                .andExpect(jsonPath("value.dcAmt").value(900))
                .andExpect(jsonPath("value.apprNO").isNotEmpty())
    			;
    }
    
    @Test
    @DisplayName("할인쿠폰 최소사용금액 오류를 확인한다.")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void dccoupon_approve_exception1_test() throws Exception {
    	String publishContent = "{\"couponCD\":\"000002\",\"userID\":\"milkit\"}";
    	
    	MvcResult publishResult = mvc.perform(MockMvcRequestBuilders.post("/api/coupon/publish").content(publishContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
    	String json = publishResult.getResponse().getContentAsString();
    	GenericResponse<Coupon> coupon = objectMapper.readValue(json, new TypeReference<GenericResponse<Coupon>>() {});
    	String couponNO = coupon.getValue().getCouponNO();
    	
    	UseApprRequest useApprRequest = new UseApprRequest("milkit", couponNO, 900l);
    	String content = objectMapper.writeValueAsString(useApprRequest);
    	
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/api/coupon/use").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    	        .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("612"))
                .andExpect(jsonPath("message").value("할인쿠폰 사용이 가능한 최소사용금액에 미치지 못합니다. 할인쿠폰 최소사용금액:1000, 할인승인요청금액:900"))
                .andExpect(jsonPath("value").isEmpty())
    			;
    }
    
    
    @Test
    @DisplayName("금액쿠폰을 취소한다.")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void billcoupon_cancel_test() throws Exception {
    	String publishContent = "{\"couponCD\":\"000001\",\"userID\":\"milkit\"}";
    	
    	MvcResult publishResult = mvc.perform(MockMvcRequestBuilders.post("/api/coupon/publish").content(publishContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
    	String json = publishResult.getResponse().getContentAsString();
    	GenericResponse<Coupon> coupon = objectMapper.readValue(json, new TypeReference<GenericResponse<Coupon>>() {});
    	String couponNO = coupon.getValue().getCouponNO();
    	
    	UseApprRequest useApprRequest = new UseApprRequest("milkit", couponNO, 9000l);
    	String useContent = objectMapper.writeValueAsString(useApprRequest);
    	
    	MvcResult useResult =  mvc.perform(MockMvcRequestBuilders.put("/api/coupon/use").content(useContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
    	String useJson = useResult.getResponse().getContentAsString();
    	GenericResponse<ApprResponse> apprResponse = objectMapper.readValue(useJson, new TypeReference<GenericResponse<ApprResponse>>() {});
    	
		CancelApprRequest cancelRequest = new CancelApprRequest("milkit", couponNO, apprResponse.getValue().getApprNO());
		String cancelContent = objectMapper.writeValueAsString(cancelRequest);
    	
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/api/coupon/cancel").content(cancelContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    	        .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("0"))
                .andExpect(jsonPath("message").value("성공했습니다"))
                .andExpect(jsonPath("value.useAmt").value(-9000))
                .andExpect(jsonPath("value.changeAmt").value(-1000))
                .andExpect(jsonPath("value.apprNO").isNotEmpty())
    			;
    }
    
    @Test
    @DisplayName("할인쿠폰을 취소한다.")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void dccoupon_cancel_test() throws Exception {
    	String publishContent = "{\"couponCD\":\"000002\",\"userID\":\"milkit\"}";
    	
    	MvcResult publishResult = mvc.perform(MockMvcRequestBuilders.post("/api/coupon/publish").content(publishContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
    	String json = publishResult.getResponse().getContentAsString();
    	GenericResponse<Coupon> coupon = objectMapper.readValue(json, new TypeReference<GenericResponse<Coupon>>() {});
    	String couponNO = coupon.getValue().getCouponNO();
    	
    	UseApprRequest useApprRequest = new UseApprRequest("milkit", couponNO, 9000l);
    	String useContent = objectMapper.writeValueAsString(useApprRequest);
    	
    	MvcResult useResult =  mvc.perform(MockMvcRequestBuilders.put("/api/coupon/use").content(useContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
    	String useJson = useResult.getResponse().getContentAsString();
    	GenericResponse<ApprResponse> apprResponse = objectMapper.readValue(useJson, new TypeReference<GenericResponse<ApprResponse>>() {});
    	
log.debug("apprResponse:"+apprResponse.toString());
    	
		CancelApprRequest cancelRequest = new CancelApprRequest("milkit", couponNO, apprResponse.getValue().getApprNO());
		String cancelContent = objectMapper.writeValueAsString(cancelRequest);
    	
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/api/coupon/cancel").content(cancelContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    	        .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("0"))
                .andExpect(jsonPath("message").value("성공했습니다"))
                .andExpect(jsonPath("value.useAmt").value(-8100))
                .andExpect(jsonPath("value.dcAmt").value(-900))
                .andExpect(jsonPath("value.apprNO").isNotEmpty())
    			;
    }
    
    
    
    @Test
    @DisplayName("금액쿠폰을 폐기한다.")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void coupon_discard_test() throws Exception {
    	String publishContent = "{\"couponCD\":\"000001\",\"userID\":\"milkit\"}";
    	
    	MvcResult publishResult = mvc.perform(MockMvcRequestBuilders.post("/api/coupon/publish").content(publishContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
    	String json = publishResult.getResponse().getContentAsString();
    	GenericResponse<Coupon> coupon = objectMapper.readValue(json, new TypeReference<GenericResponse<Coupon>>() {});
    	String couponNO = coupon.getValue().getCouponNO();
    	
    	ApprRequest apprRequest = new ApprRequest("milkit", couponNO);
    	String content = objectMapper.writeValueAsString(apprRequest);
    	
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/api/coupon/discard").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    	        .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("0"))
                .andExpect(jsonPath("message").value("성공했습니다"))
                .andExpect(jsonPath("value.apprNO").isNotEmpty())
    			;
    }
    
    @Test
    @DisplayName("할인쿠폰 폐기불가상태 오류를 확인한다.")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void coupon_discard_exception1_test() throws Exception {
    	String publishContent = "{\"couponCD\":\"000002\",\"userID\":\"milkit\"}";
    	
    	MvcResult publishResult = mvc.perform(MockMvcRequestBuilders.post("/api/coupon/publish").content(publishContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
    	String json = publishResult.getResponse().getContentAsString();
    	GenericResponse<Coupon> coupon = objectMapper.readValue(json, new TypeReference<GenericResponse<Coupon>>() {});
    	String couponNO = coupon.getValue().getCouponNO();
    	
    	UseApprRequest useApprRequest = new UseApprRequest("milkit", couponNO, 9000l);
    	String useContent = objectMapper.writeValueAsString(useApprRequest);
    	
    	mvc.perform(MockMvcRequestBuilders.put("/api/coupon/use").content(useContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    	
    	ApprRequest apprRequest = new ApprRequest("milkit", couponNO);
    	String content = objectMapper.writeValueAsString(apprRequest);
    	
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/api/coupon/discard").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    	        .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("622"))
                .andExpect(jsonPath("value").isEmpty())
    			;
    }
    
    
}
