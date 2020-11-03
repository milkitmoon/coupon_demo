/*
INSERT INTO USER_INFO(
    USER_ID, 
    PASSWORD, 
    USER_NM, 
    AUTH_ROLE,
    USE_YN, 
    INST_TIME, 
    UPD_TIME, 
    INST_USER, 
    UPD_USER
) values(
    'admin',
    '$2a$10$1rThoKu6Tt0osRcHVd98A.tiv0./T4tIUQwfRWN3bpkZFQFhf54tq', 
    '관리자', 
    'ROLE_ADMIN', 
    'Y', 
    now(), 
    now(), 
    'admin', 
    'admin'
);

INSERT INTO USER_INFO(
    USER_ID, 
    PASSWORD, 
    USER_NM, 
    AUTH_ROLE,
    USE_YN, 
    INST_TIME, 
    UPD_TIME, 
    INST_USER, 
    UPD_USER
) values(
    'test',
    '$2a$10$1rThoKu6Tt0osRcHVd98A.tiv0./T4tIUQwfRWN3bpkZFQFhf54tq', 
    '김복돌', 
    'ROLE_MEMBER', 
    'Y', 
    now(), 
    now(), 
    'admin', 
    'admin'
);


INSERT INTO COUPON_CRIT(
    SEQ, 
    COUPON_CRIT_CD,
	COUPON_CRIT_NM, 
	FACE_AMT, 
	DESCRIPTION,
	SORT_ORDER,
	USE_YN,
	INST_TIME,
	UPD_TIME,
	INST_USER,
	UPD_USER
) values(
    NEXTVAL('COUPON_CRIT_SEQ'),
    '0001', 
    '1만원권', 
    10000,
    '테스트 1만원권',
    1,
    'Y', 
    now(), 
    now(), 
    'admin', 
    'admin'
);

INSERT INTO COUPON_INFO(
    SEQ,
    COUPON_CRIT_CD,
	COUPON_CD,
	COUPON_NM,
	PUB_DIV,
	COUPON_DIV,
	PUB_AMT,
	CHANGE_MIN_USE_RATE,
	FEE_RATE,
	EXPIRY_DIV,
	EXPIRY_DAY,
	DUP_USE_YN,
	EXC_USE_YN,
	CHANGE_YN,
	DESCRIPTION,
	OPERATE_STATUS,
	USE_YN,
	INST_TIME,
	UPD_TIME,
	INST_USER,
	UPD_USER
) values(
    NEXTVAL('COUPON_INFO_SEQ'),
    '0001',
    '000001', 
    '테스트쿠폰', 
    '1',
    '10',
    9000,
    60.0,
    50.0,
    '2',
    365,
    'N',
    'N',
    'Y',
    '테스트 쿠폰정보',
    '2',
    'Y', 
    now(), 
    now(), 
    'admin', 
    'admin'
);
*/

