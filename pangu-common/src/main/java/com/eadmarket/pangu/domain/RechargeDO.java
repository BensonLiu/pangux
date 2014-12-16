package com.eadmarket.pangu.domain;

import com.eadmarket.pangu.common.IEnum;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 充值记录
 *
 * @author liuyongpo@gmail.com
 */
@Data
@ToString
public class RechargeDO {

  /**
   * 充值记录编号
   */
  private Long id;
  /**
   * 会员编号
   */
  private Long userId;
  /**
   * 充值记录状态
   */
  private RechargeStatus status;
  /**
   * 充值金额，以分为单位
   */
  private Long cash;
  /**
   * 充值渠道
   */
  private ChannelType channelType;
  /**
   * 外部编号，可能用户对账
   */
  private Long outOrderId;
  /**
   * 记录创建时间
   */
  private Date gmtCreate;

  public boolean isNew() {
    return status == RechargeStatus.NEW;
  }

  public boolean isCompleted() {
    return status == RechargeStatus.COMPELETED;
  }

  public static enum ChannelType implements IEnum {
    ALIPAY(101, "支付宝"),
    CFT(102, "财付通"),;

    @Getter
    private final int code;

    @Getter
    private final String desc;

    private ChannelType(int code, String desc) {
      this.code = code;
      this.desc = desc;
    }

  }

  public static enum RechargeStatus implements IEnum {
    NEW(1, "待付款"),
    COMPELETED(2, "已完成"),;

    @Getter
    private final int code;

    @Getter
    private final String desc;

    private RechargeStatus(int code, String desc) {
      this.code = code;
      this.desc = desc;
    }

  }
}
