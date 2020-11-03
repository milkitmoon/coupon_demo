package com.milkit.app.api.userinfo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import com.milkit.app.api.userinfo.AccountController;
import com.milkit.app.api.userinfo.UserInfoController;
import com.milkit.app.common.AppCommon;
import com.milkit.app.config.WebSecurityConfigure;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;


@WebMvcTest(AccountController.class) 
@Import(WebSecurityConfigure.class)
@Slf4j
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;
    
    
    @MockBean
    private UserInfoServiceImpl userInfoServie;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
  
    @Test
    @WithMockUser(username = "test", roles = {"MEMBER"})
    public void refresh_테스트() throws Exception {
    	UserInfo userInfo = user();
		String token = jwtTokenProvider.createRefreshToken(userInfo);
		
        //given
		JwtToken jwtToken = jwtTokenProvider.createBody(userInfo);
        given(userInfoServie.refresh(token)).willReturn(jwtToken);

        //when
        final ResultActions actions = mvc.perform(get("/refresh")
        		.header(AppCommon.getInstance().JWT_HEADER_STRING, AppCommon.getInstance().JWT_TOKEN_PREFIX + token)
        		.accept("application/json;charset=UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        actions
                .andExpect(status().isOk())
                ;
    }
 


    
    private UserInfo user() {
		String password = "$2a$10$1rThoKu6Tt0osRcHVd98A.tiv0./T4tIUQwfRWN3bpkZFQFhf54tq";	//test
		
		UserInfo userInfo = new UserInfo("test", password);
		userInfo.setUserNM("김복돌");
		userInfo.setAuthRole("ROLE_MEMBER");
		userInfo.setUseYN("Y");
		userInfo.setInstTime(new Date());
		userInfo.setUpdTime(new Date());
		userInfo.setInstUser("admin");
		userInfo.setUpdUser("admin");
		
		return userInfo;
    }
    
    
}
