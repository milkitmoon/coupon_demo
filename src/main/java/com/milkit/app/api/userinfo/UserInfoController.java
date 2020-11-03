package com.milkit.app.api.userinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(tags = "4. 사용자 정보", value = "UserInfoController")
public class UserInfoController extends AbstractApiController {

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @GetMapping("/api/userinfo")
    @ApiOperation(value = "사용자정보 전체조회 ", notes = "사용자정보 전체 목록을 조회한다.")
    public ResponseEntity<GenericResponse<List<UserInfo>>> userinfo() throws Exception {
        return apiResponse(() -> userInfoService.selectAll());
    }


    
}