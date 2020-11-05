package com.milkit.app.api.coupon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milkit.app.api.AbstractApiController;
import com.milkit.app.api.coupon.request.PublishRequest;
import com.milkit.app.api.coupon.request.ApprRequest;
import com.milkit.app.api.coupon.request.CancelApprRequest;
import com.milkit.app.api.coupon.request.UseApprRequest;
import com.milkit.app.api.coupon.response.ApprResponse;
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;
import com.milkit.app.common.response.GenericResponse;
import com.milkit.app.config.jwt.JwtToken;
import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.domain.coupon.Coupon;
import com.milkit.app.domain.coupon.approve.CouponApproveHandlerService;
import com.milkit.app.domain.coupon.approve.cancel.service.CouponCancelApproveHandlerServiceImpl;
import com.milkit.app.domain.coupon.approve.discard.service.CouponDiscardHandlerServiceImpl;
import com.milkit.app.domain.coupon.approve.use.service.CouponUseApproveHandlerServiceImpl;
import com.milkit.app.domain.coupon.publish.service.CouponPublishHandlerServiceImpl;
import com.milkit.app.domain.coupon.query.service.CouponQueryHandlerServiceImpl;
import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/coupon")
@Api(tags = "1. 쿠폰 발행", value = "CouponPublishController")
public class CouponPublishController extends AbstractApiController {


	@Autowired
	private CouponPublishHandlerServiceImpl couponPublishManagerService;
	
	
	@PostMapping(value = "/publish")
	@ApiOperation(value = "쿠폰발행", notes = "신규쿠폰을 발행하여 전송한다.")
    public ResponseEntity<GenericResponse<Coupon>> publish(
    		@ApiParam(value = "발행요청정보", required = true) @RequestBody final PublishRequest publishRequest
			) throws Exception {
		
    	return apiResponse(() -> couponPublishManagerService.approve(publishRequest));
    }
	

}