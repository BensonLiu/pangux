package com.eadmarket.pangu;

import lombok.Getter;

/**
 * @author liuyongpo@gmail.com
 */
public enum ExceptionCode {
  SYSTEM_ERROR(501, "系统开小差中"),

  USER_NOT_EXIST(101, "用户名不存在或者密码错误"),
  PWD_NOT_MATCH(102, "用户名不存在或者密码错误"),
  USER_REGISTED(103, "email已经被注册了"),

  RECHARGE_NOT_EXIST(201, "充值记录不存在"),
  DELETED_RECHARGE(202, "充值记录已删除"),

  POSITION_NOT_EXIST(301, "广告位不存在"),
  POSITION_NOT_ON_SALE(302, "广告位已经售出"),

  ACCOUNT_HAVE_NO_ENGHOU_MONEY(401, "金额不足"),;

  @Getter
  private final int code;
  @Getter
  private final String desc;

  private ExceptionCode(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public boolean isSystemError() {
    return this == SYSTEM_ERROR;
  }
}
