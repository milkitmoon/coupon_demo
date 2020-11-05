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
@Api(tags = "2. 쿠폰 승인", value = "CouponApprController")
public class CouponApprController extends AbstractApiController {


	@Autowired
	private CouponUseApproveHandlerServiceImpl couponApproveManagerService;
	
	@Autowired
	private CouponCancelApproveHandlerServiceImpl couponCancelApproveManagerService;
	
	@Autowired
	private CouponDiscardHandlerServiceImpl couponDiscardApproveManagerService;


	
	@PutMapping(value = "/use")
	@ApiOperation(value = "쿠폰사용", notes = "쿠폰을 사용한다.")
    public ResponseEntity<GenericResponse<ApprResponse>> approve(
    		@ApiParam(value = "사용요청정보", required = true) @RequestBody final UseApprRequest apprRequest
			) throws Exception {
		
    	return apiResponse(() -> couponApproveManagerService.approve(apprRequest));
    }
	
	@PutMapping(value = "/cancel")
	@ApiOperation(value = "쿠폰취소", notes = "쿠폰을 취소한다.")
    public ResponseEntity<GenericResponse<ApprResponse>> cancel(
    		@ApiParam(value = "쿠폰취소요청정보", required = true) @RequestBody final CancelApprRequest apprRequest
			) throws Exception {
		
    	return apiResponse(() -> couponCancelApproveManagerService.approve(apprRequest));
    }
    
	@PutMapping(value = "/discard")
	@ApiOperation(value = "쿠폰폐기", notes = "쿠폰을 폐기한다.")
    public ResponseEntity<GenericResponse<ApprResponse>> discard(
    		@ApiParam(value = "쿠폰폐기요청정보", required = true) @RequestBody final ApprRequest apprRequest
			) throws Exception {
		
    	return apiResponse(() -> couponDiscardApproveManagerService.approve(apprRequest));
    }

	
}