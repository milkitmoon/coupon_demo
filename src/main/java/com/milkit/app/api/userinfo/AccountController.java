package com.milkit.app.api.userinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milkit.app.api.AbstractApiController;
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.common.response.GenericResponse;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@Api(tags = "5. 계정정보 관리", value = "AccountController")
public class AccountController extends AbstractApiController {

    @Autowired
    private UserInfoServiceImpl userInfoService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    
    @GetMapping("/refresh")
    @ApiOperation(value = "사용자토큰 재발급 ", notes = "사용자토큰 정보를 재발급한다.")
    public ResponseEntity<GenericResponse<JwtToken>> refresh(
    			@RequestHeader("Authorization") String token
    		) throws Exception {
        return apiResponse(() -> userInfoService.refresh(token));
    }

}