/*

		
-- [쿠폰기본정보]
CREATE TABLE `COUPON_INFO` (
	`SEQ`                 BIGINT       NOT NULL COMMENT '일련번호', -- 일련번호
	`COUPON_CD`           VARCHAR(6)   NOT NULL COMMENT '쿠폰마스터코드 (시퀀스 6자리)', -- 쿠폰코드
	`COUPON_NM`           VARCHAR(60)  NULL     COMMENT '쿠폰명', -- 쿠폰명
	`PUB_DIV`             CHAR(1)      NOT NULL COMMENT '쿠폰발행구분 (1:등록과 동시에 자동발행, 2:사전등록 후 임의발행)', -- 쿠폰발행구분
	`COUPON_DIV`          CHAR(2)      NOT NULL DEFAULT '1' COMMENT '쿠폰구분 (10:금액권, 20:할인권, 30:교환권)', -- 쿠폰구분
	`MAX_PUB_QTY`         INT          NULL     DEFAULT -1 COMMENT '최대발행쿠폰수량 (0 혹은 null일 경우 무제한)', -- 최대발행쿠폰수량
	`PUB_AMT`             BIGINT       NULL     COMMENT '발행금액 (정산가치금액)', -- 발행금액
	`DC_RATE`             DECIMAL(5,2) NULL     COMMENT '금액할인율 (쿠폰구분이 할인권일때 금액할인율)', -- 할인권 금액할인율
	`DC_POSS_MIN_AMT`     BIGINT       NULL     COMMENT '쿠폰구분이 할인권일시 할인권 사용이 가능한 최소사용금액  (0일 경우 무제한)', -- 할인권 최소사용금액
	`DC_MAX_AMT`          BIGINT       NULL     COMMENT '할인권에서 할인이 가능한 최대 할인금액  (0일 경우 무제한)', -- 할인권 최대할인금액
	`CHANGE_MIN_USE_RATE` DECIMAL(5,2) NULL     COMMENT '거스름돈 지급금액비율 (금액권의 경우 일반적으로 60%,  최대 거스름돈금액 = 액면금액 * (100%-CHANGE_MIN_USE_RATE)))', -- 거스름돈지급금액비율
	`FEE_RATE`            DECIMAL(5,2) NULL     COMMENT '정산수수료율 (ex.쿠폰별 정산수수료, 수수료 = 발행금액 X 수수료율)', -- 수수료율
	`EXPIRY_DIV`          CHAR(1)      NULL     DEFAULT '2' COMMENT '쿠폰승인 유효기간 구분 (1:승인종료일, 2: 유효기간일자)', -- 유효기간구분
	`APPR_START_DT`       VARCHAR(8)   NULL     COMMENT '승인시작일 (YYYYMMDD)', -- 승인시작일자
	`APPR_END_DT`         VARCHAR(8)   NULL     COMMENT '승인종료일 (YYYYMMDD)', -- 승인종료일자
	`EXPIRY_DAY`          INT          NULL     COMMENT '쿠폰승인 유효기간(쿠폰 발행일로부터의 유효일자)', -- 유효기간
	`DUP_USE_YN`          CHAR(1)      NULL     DEFAULT 'Y' COMMENT '중복사용 가능여부(쿠폰을 2장이상 사용가능여부)', -- 중복사용 가능여부
	`EXC_USE_YN`          CHAR(1)      NULL     DEFAULT 'Y' COMMENT '액면가이상 사용해야 하는지 여부', -- 액면가이상사용여부
	`CHANGE_YN`           CHAR(1)      NULL     DEFAULT 'Y' COMMENT '거스름돈여부 (ex. 1만원짜리 금액권 사용시 9000원만 사용했다면 나머지 거스름돈을 주는지)', -- 거스름돈여부
	`DESCRIPTION`         VARCHAR(512) NULL     COMMENT '상세설명', -- 상세설명
	`OPERATE_STATUS`      CHAR(1)      NULL     DEFAULT '1' COMMENT '운영상태 (1:등록, 2:확정)', -- 운영상태
	`USE_YN`              CHAR(1)      NULL     DEFAULT '1' COMMENT '사용여부 (Y:사용,N:미사용)', -- 사용여부
	`INST_TIME`           TIMESTAMP    NULL     COMMENT '등록시간', -- 등록시간
	`UPD_TIME`            TIMESTAMP    NULL     COMMENT '갱신시간', -- 갱신시간
	`INST_USER`           VARCHAR(20)  NULL     COMMENT '등록자', -- 등록자
	`UPD_USER`            VARCHAR(20)  NULL     COMMENT '갱신자' -- 갱신자
)
COMMENT '쿠폰기본정보';

-- 쿠폰기본정보
ALTER TABLE `COUPON_INFO`
	ADD CONSTRAINT `IDX_COUPON_INFO_PK` -- IDX_COUPON_INFO_PK
		PRIMARY KEY (
			`SEQ` -- 일련번호
		);

-- UIX_COUPON_INFO
CREATE UNIQUE INDEX `UIX_COUPON_INFO`
	ON `COUPON_INFO` ( -- 쿠폰기본정보
		`COUPON_CD` ASC -- 쿠폰코드
	);

-- 쿠폰기본정보 인덱스
CREATE INDEX `IX_COUPON_INFO`
	ON `COUPON_INFO`( -- 쿠폰기본정보
		`COUPON_CRIT_CD` ASC, -- 권종코드
		`COUPON_CD` ASC,      -- 쿠폰코드
		`PUB_DIV` ASC,        -- 쿠폰발행구분
		`COUPON_DIV` ASC,     -- 쿠폰구분
		`COUPON_NM` ASC,      -- 쿠폰명
		`OPERATE_STATUS` ASC, -- 운영상태
		`USE_YN` ASC          -- 사용여부
	);

-- 쿠폰기본정보 인덱스2
CREATE INDEX `IX_COUPON_INFO2`
	ON `COUPON_INFO`( -- 쿠폰기본정보
		`DUP_USE_YN` ASC,     -- 중복사용 가능여부
		`EXC_USE_YN` ASC,     -- 액면가이상사용여부
		`CHANGE_YN` ASC,      -- 거스름돈여부
		`OPERATE_STATUS` ASC, -- 운영상태
		`USE_YN` ASC          -- 사용여부
	);


-- [쿠폰]
CREATE TABLE `COUPON` (
	`SEQ`           BIGINT       NOT NULL COMMENT '일련번호', -- 일련번호
	`COUPON_CD`     VARCHAR(6)   NOT NULL COMMENT '쿠폰폰마스터코드', -- 쿠폰코드
	`COUPON_NO`     VARCHAR(28)  NOT NULL COMMENT '쿠폰번호 [쿠폰구분(2)+쿠폰코드(6)+쿠폰순번(9)+체크섬(2)]', -- 쿠폰번호
	`COUPON_SEQ`    INT          NULL     DEFAULT 0 COMMENT '쿠폰등록순번 (쿠폰코드 별로 1부터 순차적으로 늘어남)', -- 쿠등록순번
	`USER_ID`       VARCHAR(60)  NULL     COMMENT '쿠폰발급 대상계정 (임의로 배포되는 쿠폰일 경우 없을수도 있음)', -- 쿠폰발급계정
	`REGIST_DT`     VARCHAR(8)   NULL     COMMENT '쿠폰등록일자 (YYYYMMDD)', -- 등록일자
	`PUB_DT`        VARCHAR(8)   NULL     COMMENT '쿠폰발행일자 (YYYYMMDD)', -- 발행일자
	`APPR_START_DT` VARCHAR(8)   NULL     COMMENT '쿠폰승인시작일 (YYYYMMDD)', -- 승인시작일자
	`EXPIRY_DT`     VARCHAR(8)   NULL     COMMENT '쿠폰승인유효일자 (YYYYMMDD)', -- 유효일자
	`PUB_TIME`      TIMESTAMP    NULL     COMMENT '쿠폰발행시간', -- 발행시간
	`USE_TIME`      TIMESTAMP    NULL     COMMENT '쿠폰사용시간', -- 사용시간
	`FACE_AMT`      BIGINT       NULL     COMMENT '액면금액', -- 액면금액
	`PUB_AMT`       BIGINT       NULL     COMMENT '발행금액', -- 발행금액
	`USE_AMT`       BIGINT       NULL     COMMENT '사용금액', -- 사용금액
	`DC_RATE`       DECIMAL(5,2) NULL     COMMENT '할인율 (쿠폰구분이 할인권일때 금액할인율)', -- 할인율
	`STATUS`        CHAR(1)      NOT NULL DEFAULT '1' COMMENT '쿠폰상태 (1:등록, 2:발행, 3:사용, 4:반품, 5:폐기)', -- 쿠폰상태
	`USE_YN`        CHAR(1)      NULL     DEFAULT 'Y' COMMENT '사용여부 (Y:사용,N:미사용)', -- 사용여부
	`INST_TIME`     TIMESTAMP    NULL     COMMENT '등록시간', -- 등록시간
	`UPD_TIME`      TIMESTAMP    NULL     COMMENT '갱신시간', -- 갱신시간
	`INST_USER`     VARCHAR(20)  NULL     COMMENT '등록자', -- 등록자
	`UPD_USER`      VARCHAR(20)  NULL     COMMENT '갱신자' -- 갱신자
)
COMMENT '쿠폰';

-- 쿠폰
ALTER TABLE `COUPON`
	ADD CONSTRAINT `IDX_COUPON_PK` -- IDX_COUPON_PK
		PRIMARY KEY (
			`SEQ` -- 일련번호
		);

-- UIX_COUPON
CREATE UNIQUE INDEX `UIX_COUPON`
	ON `COUPON` ( -- 쿠폰
		`COUPON_NO` ASC -- 쿠폰번호
	);

-- 쿠폰 인덱스
CREATE INDEX `IX_COUPON`
	ON `COUPON`( -- 쿠폰
		`COUPON_CD` ASC, -- 쿠폰코드
		`COUPON_NO` ASC, -- 쿠폰번호
		`STATUS` ASC     -- 쿠폰상태
	);

-- 쿠폰 인덱스2
CREATE INDEX `IX_COUPON2`
	ON `COUPON`( -- 쿠폰
		`COUPON_CD` ASC,  -- 쿠폰코드
		`COUPON_SEQ` ASC, -- 쿠등록순번
		`USE_YN` ASC      -- 사용여부
	);

-- 쿠폰 인덱스3
CREATE INDEX `IX_COUPON3`
	ON `COUPON`( -- 쿠폰
		`COUPON_NO` ASC,     -- 쿠폰번호
		`APPR_START_DT` ASC, -- 승인시작일자
		`EXPIRY_DT` ASC,     -- 유효일자
		`USE_YN` ASC,        -- 사용여부
		`STATUS` ASC         -- 쿠폰상태
	);

ALTER TABLE `COUPON`
	MODIFY COLUMN `SEQ` BIGINT NOT NULL AUTO_INCREMENT COMMENT '일련번호';
	

-- [쿠폰거래로그]
CREATE TABLE `COUPON_LOG` (
	`SEQ`            BIGINT      NOT NULL COMMENT '일련번호', -- 일련번호
	`SALE_DT`        VARCHAR(8)  NULL     COMMENT '거래래일자 (YYYYMMDD)', -- 거래일자
	`COUPON_NO`      VARCHAR(28) NOT NULL COMMENT '쿠폰번호', -- 쿠폰번호
	`PRODUCT_CD`     VARCHAR(20) NULL     COMMENT '교환권일 경우 교환상품코드', -- 교환상품코드
	`TRADE_TYPE`     CHAR(1)     NOT NULL DEFAULT '1' COMMENT '거래유형 (0:정상거래, 1:거래취소, 2:반품거래)', -- 거래유형
	`TRADE_DIV`      CHAR(1)     NULL     COMMENT '거래구분 (1:발행, 2:조회, 3:사용, 4:반품, 5:폐기)', -- 거래구분
	`APPR_NO`        VARCHAR(36) NOT NULL COMMENT '거래번호', -- 거래번호
	`RTN_APPR_NO`    VARCHAR(36) NULL     COMMENT '반품원거래번호 (반품거래 시 원거래번호표기)', -- 반품거래번호
	`TRANSACTION_ID` VARCHAR(36) NULL     COMMENT '망전송식별번호 (Client에서 발행)', -- 망전송식별번호
	`APPR_TIME`      TIMESTAMP   NULL     COMMENT '승인시간', -- 승인시간
	`FACE_AMT`       BIGINT      NULL     COMMENT '액면금액', -- 액면금액
	`PUB_AMT`        BIGINT      NULL     COMMENT '발행금액', -- 발행금액
	`REQ_USE_AMT`    BIGINT      NULL     COMMENT '사용/할인 요청금액 (Client의 승인요청 금액)', -- 사용요청금액
	`USE_AMT`        BIGINT      NULL     COMMENT '사용/할인 승인금액 (실제 승인금액, 1. 금액권 : 사용요청금액이 액면금액보다 적을 시 사용금액이 조정된다, 2. 할인권 : 사용요청금액에서 할인금액을 제한 금액이 표기된다.)', -- 사용금액
	`CHANGE_AMT`     BIGINT      NULL     COMMENT '거스름돈 금액 (금액권에서 승인시 거스름돈 금액)', -- 거스름돈금액
	`DC_AMT`         BIGINT      NULL     COMMENT '할인금액 (할인쿠폰 승인 시 할인된 금액)', -- 할인금액
	`RESULT_CD`      VARCHAR(4)  NULL     DEFAULT '0' COMMENT '결과코드 (''0''일 경우 성공 그 외 실패)', -- 결과코드
	`INST_TIME`      TIMESTAMP   NULL     COMMENT '등록시간', -- 등록시간
	`UPD_TIME`       TIMESTAMP   NULL     COMMENT '갱신시간', -- 갱신시간
	`INST_USER`      VARCHAR(20) NULL     COMMENT '등록자', -- 등록자
	`UPD_USER`       VARCHAR(20) NULL     COMMENT '갱신자' -- 갱신자
)
COMMENT '쿠폰거래로그';

-- 쿠폰거래로그
ALTER TABLE `COUPON_LOG`
	ADD CONSTRAINT `IDX_COUPON_LOG_PK` -- IDX_COUPON_LOG_PK
		PRIMARY KEY (
			`SEQ` -- 일련번호
		);

-- 쿠폰거래로그 인덱스
CREATE INDEX `IX_COUPON_LOG`
	ON `COUPON_LOG`( -- 쿠폰거래로그
		`SALE_DT` ASC,    -- 거래일자
		`COUPON_NO` ASC,  -- 쿠폰번호
		`PRODUCT_CD` ASC, -- 교환상품코드
		`TRADE_TYPE` ASC, -- 거래유형
		`TRADE_DIV` ASC,  -- 거래구분
		`RESULT_CD` ASC   -- 결과코드
	);

-- 쿠폰거래로그 인덱스2
CREATE INDEX `IX_COUPON_LOG2`
	ON `COUPON_LOG`( -- 쿠폰거래로그
		`APPR_NO` ASC,   -- 거래번호
		`RESULT_CD` ASC  -- 결과코드
	);

-- 쿠폰거래로그 인덱스3
CREATE INDEX `IX_COUPON_LOG3`
	ON `COUPON_LOG`( -- 쿠폰거래로그
		`COUPON_NO` ASC,   -- 쿠폰번호
		`APPR_NO` ASC,     -- 거래번호
		`RTN_APPR_NO` ASC, -- 반품거래번호
		`RESULT_CD` ASC    -- 결과코드
	);

-- 쿠폰거래로그 인덱스4
CREATE INDEX `IX_COUPON_LOG4`
	ON `COUPON_LOG`( -- 쿠폰거래로그
		`COUPON_NO` ASC,      -- 쿠폰번호
		`TRANSACTION_ID` ASC  -- 망전송식별번호
	);

ALTER TABLE `COUPON_LOG`
	MODIFY COLUMN `SEQ` BIGINT NOT NULL AUTO_INCREMENT COMMENT '일련번호';


-- [쿠폰번호순번]
CREATE TABLE `COUPON_NO_SEQ` (
	`COUPON_CD` VARCHAR(6) NOT NULL COMMENT '쿠폰마스터코드 (시퀀스 6자리)', -- 쿠폰코드
	`SEQ`       BIGINT     NOT NULL COMMENT '일련번호' -- 일련번호
);

-- 쿠폰번호순번
ALTER TABLE `COUPON_NO_SEQ`
	ADD CONSTRAINT `PK_COUPON_NO_SEQ` -- 쿠폰번호순번 기본키
		PRIMARY KEY (
			`COUPON_CD` -- 쿠폰코드
		);

-- 쿠폰번호순번 유니크 인덱스
CREATE UNIQUE INDEX `UIX_COUPON_NO_SEQ`
	ON `COUPON_NO_SEQ` ( -- 쿠폰번호순번
		`COUPON_CD` ASC, -- 쿠폰코드
		`SEQ` ASC        -- 일련번호
	);
	

-- [쿠폰기본정보 SEQUENCE]
CREATE SEQUENCE COUPON_INFO_SEQ START WITH 1 INCREMENT BY 1;

-- [사용자정보]
CREATE TABLE `USER_INFO` (
	`ID`        BIGINT       NOT NULL COMMENT '일련번호', -- 일련번호
	`USER_ID`   VARCHAR(60)  NOT NULL COMMENT '사용자아이디', -- 사용자아이디
	`PASSWORD`  VARCHAR(512) NOT NULL COMMENT '비밀번호', -- 비밀번호
	`USER_NM`   VARCHAR(60)  NULL,     -- 사용자명
	`AUTH_ROLE` VARCHAR(20)  NULL,     -- 권한
	`USE_YN`    CHAR(1)      NULL     COMMENT '사용여부', -- 사용여부
	`INST_TIME` TIMESTAMP    NULL     COMMENT '등록시간', -- 등록시간
	`UPD_TIME`  TIMESTAMP    NULL     COMMENT '갱신시간', -- 갱신시간
	`INST_USER` VARCHAR(60)  NULL     COMMENT '등록자', -- 등록자
	`UPD_USER`  VARCHAR(60)  NULL     COMMENT '갱신자' -- 갱신자
)
COMMENT '사용자정보';

-- 사용자정보
ALTER TABLE `USER_INFO`
	ADD CONSTRAINT `IDX_POS_USER_PK` -- IDX_POS_USER_PK
		PRIMARY KEY (
			`ID` -- 일련번호
		);

-- UIX_USER_INFO
CREATE UNIQUE INDEX `UIX_USER_INFO`
	ON `USER_INFO` ( -- 사용자정보
		`USER_ID` ASC -- 사용자아이디
	);

-- IX_USER_INFO
CREATE INDEX `IX_USER_INFO`
	ON `USER_INFO`( -- 사용자정보
		`USER_ID` ASC,   -- 사용자아이디
		`USER_NM` ASC,   -- 사용자명
		`USE_YN` ASC,    -- 사용여부
		`INST_USER` ASC, -- 등록자
		`INST_TIME` ASC  -- 등록시간
	);

ALTER TABLE `USER_INFO`
	MODIFY COLUMN `ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT '일련번호';
	
	
	
*/
