package com.milkit.app.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.milkit.app.common.pattern.PatternMacherServiceImpl;


public enum ErrorCodeEnum {
	
	OK("0", "성공했습니다."), 
	RESPONSE_OK("200", "전송이 성공하였습니다."), 
	RESPONSE_FAIL("299", "전송이 실패하였습니다."),
	
	ValidateException("301", "검증오류가 발생하였습니다."),
	AttemptAuthenticationException("302", "검증오류가 발생하였습니다."),
	
	NotExistCouponInfoException("601", "쿠폰기본정보가 존재하지 않습니다. 요청쿠폰코드:#{0}"),
	NotUseCouponInfoException("602", "미사용 중인 쿠폰기본정보입니다."),
	OperateStatusNotConfirmException("603", "쿠폰기본정보의 운영상태가 미확정입니다. 운영상태 :#{0}"),
	MaxPubQtyException("604", "최대발행쿠폰수량을 초과하였습니다. 최대발행쿠폰수량:#{0}, 현재발행쿠폰수량:#{1}"),
	NotExistPreRegistCouponException("605", "발행할 수 있는 쿠폰정보가 없습니다. 요청쿠폰코드:#{0}"),
	CouponAlreadyUseException("606", "이미 사용된 쿠폰입니다."),
	CouponStatusNotPublishException("607", "쿠폰이 발행상태가 아닙니다. 쿠폰상태코드:#{0}"),
	NotUseCouponException("608", "미사용 중인 쿠폰입니다"),
	CouponApprExpireTimeException("609", "쿠폰 승인유효일자가 아닙니다. 승인시작일자:#{0}, 승인유효일자:#{1}"),
	NotExistPubDivException("610", "존재하지 않는 쿠폰발행구분입니다. 쿠폰발행구분코드:#{0}"),
	NotExistCouponDivException("611", "존재하지 않는 쿠폰구분입니다. 쿠폰구분코드:#{0}"),
	DcPossMinAmtException("612", "할인쿠폰 사용이 가능한 최소사용금액에 미치지 못합니다. 할인쿠폰 최소사용금액:#{0}, 할인승인요청금액:#{1}"),
	NotExistCouponException("613", "존재하지 않는 쿠폰입니다. 쿠폰번호:#{0}"),
	NotExistCxlCouponLogException("614", "해당 승인번호로 사용거래가 존재하지 않습니다. 승인번호:#{0}"),
	GenerateCouponLogException("615", "쿠폰로그 생성에 실패하였습니다."),
	IncorrectCancelCouponNumberExeption("616", "취소대상 거래의 쿠폰번호와 취소요청 쿠폰번호가 일치하지 않습니다. 취소요청 쿠폰번호:#{0}, 취소요청 거래번호:#{1}"),
	UnableCanceledCancelTradeException("617", "취소된 거래건은 취소할 수 없습니다. 취소요청 거래번호:#{0}, 취소요청 거래유형:#{1}"),
	InvalidCancelTradeDivException("618", "사용 거래만 취소할 수 있습니다. 취소요청 거래구분:#{0}"),
	AlreadyCancelApprNoException("619", "이미 취소된 쿠폰은 취소할 수 없습니다. 취소요청 거래번호:#{0}"),
	UnableCancelCouponStatusException("620", "쿠폰상태가 사용완료가 아닌 쿠폰은 취소할 수 없습니다. 취소요청 쿠폰번호:#{0}"),
	InvalidCxlCouponLogResultException("621", "취소할 거래정보의 결과가 성공이 아닌 경우는 취소할 수 없습니다. 취소요청 거래번호:#{0}, 취소요청 거래결과:#{1}"),
	UnableDiscardCouponStatusException("622", "등록 혹은 발행 상태의 쿠폰이 아니면 폐기할 수 없음. 퍠기요청 쿠폰번호:#{0}, 폐기요청 쿠폰상태:#{1}"),
	CouponReqUseAmtException("623", "사용요청 금액은 0보다 커야합니다. 사용요청금액:#{0}"),
	
	
	IllegalRolyType("701", "올바르지 못한 사용자 권한입니다. 권한:#{0}"),
	ExistedAlreadyUser("702", "올바르지 못한 사용자 권한입니다. 권한:#{0}"),
	NotExistUserException("703", "존재하지 않는 사용자입니다."),
	LoginParameterException("704", "로그인 정보가 올바르지 않습니다."),
	LoginPasswordNotMatchException("705", "로그인 비밀번호를 확인해 주세요."),
	
	NotSupportExcelType("801", "지원하지 않는 Excel 형식입니다. 요청형식:#{0}"),
	ExcelParsingFail("802", "Excel 문서 Parsing 오류입니다."),
	StorageException("803", "파일저장 시 오류가 발생했습니다."),
	
	DatabaseException("881", "데이터베이스 오류입니다."),
	BatchInsertException("882", "배치등록 오류입니다."),
	DuplicationException("883", "데이터베이스에 중복된 정보가 있습니다."),
	
	ServiceException("900", "서비스 오류입니다."),
	SystemError("999", "시스템오류가 발생했습니다.");


	private String code;
    private String message;
    

    ErrorCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
    
	public String getCode() {		
		return code;
	}
	
	public String getMessage() {	
		return message;
	}
	
	public String getMessage(String[] objs) {
		String remixMessage = null;
		try {
			remixMessage = PatternMacherServiceImpl.getMachingMessage(this.message, objs);
		} catch (Exception ex) {
			remixMessage = this.message;
		}
		
		return remixMessage;
	}
	
	public static String getMessage(String code) {
		for(ErrorCodeEnum t: ErrorCodeEnum.values()) {
			if( t.getCode() == code ) {
				return t.getMessage();
			}
		}
		
		return "";
	}
	
	public static String getMessage(String code, String[] objs) {
		for(ErrorCodeEnum t: ErrorCodeEnum.values()) {
			if( t.getCode() == code ) {
				return t.getMessage(objs);
			}
		}
		
		return null;
	}

	
}