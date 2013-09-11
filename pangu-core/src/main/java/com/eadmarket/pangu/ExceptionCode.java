package com.eadmarket.pangu;

import lombok.Getter;

/**
 * @author liuyongpo@gmail.com
 */
public enum ExceptionCode {
	SYSTEM_ERROR(501),
	
	USER_NOT_EXIST(101),
	PWD_NOT_MATCH(102),
	USER_REGISTED(103),
	
	RECHARGE_NOT_EXIST(201),
	DELETED_RECHARGE(202),
	
	POSITION_NOT_EXIST(301),
	POSITION_NOT_ON_SALE(302),
	
	ACCOUNT_HAVE_NO_ENGHOU_MONEY(401),
	;
	
	@Getter private final int code;
	
	private ExceptionCode(int code) {
		this.code = code;
	}
	
	public boolean isSystemError() {
		return this == SYSTEM_ERROR;
	}
}
