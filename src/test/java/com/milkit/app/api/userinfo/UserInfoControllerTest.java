package com.milkit.app.api.userinfo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;


import com.milkit.app.api.userinfo.UserInfoController;
import com.milkit.app.config.WebSecurityConfigure;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;

import lombok.extern.slf4j.Slf4j;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.web.bind.annotation.RequestParam;

@WebMvcTest(UserInfoController.class) 
@Import(WebSecurityConfigure.class)
@Slf4j
public class UserInfoControllerTest {

    @Autowired
    private MockMvc mvc;
    
    
    @MockBean
    private UserInfoServiceImpl userInfoServie;
    
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void userinfoAll_테스트() throws Exception {
        //given
    	List<UserInfo> list = Arrays.asList(new UserInfo(1L, "admin", "ROLE_ADMIN"), new UserInfo(2L, "test", "ROLE_MEMBER"));
        given(userInfoServie.selectAll()).willReturn(list);

        //when
        final ResultActions actions = mvc.perform(get("/api/userinfo")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("value[0].id").value(1L))
                .andExpect(jsonPath("value[0].userID").value("admin"))
                .andExpect(jsonPath("value[0].authRole").value("ROLE_ADMIN"))
                .andExpect(jsonPath("value[1].id").value(2L))
                .andExpect(jsonPath("value[1].userID").value("test"))
                .andExpect(jsonPath("value[1].authRole").value("ROLE_MEMBER"))
                ;
    }
    
 
    
    
}
