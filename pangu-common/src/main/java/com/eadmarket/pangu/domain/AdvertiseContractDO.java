package com.eadmarket.pangu.domain;

import lombok.Data;
import lombok.ToString;

/**
 * 广告位的契约表，广告位的展示信息
 */
@Data
@ToString
public final class AdvertiseContractDO {

  public final static Integer VALID_STATUS = 1;

  public final static Integer INVALID_STATUS = 2;

  /**
   * 契约编号
   */
  private Long id;
  /**
   * 所附属的广告位编号
   */
  private Long advertiseId;
  /**
   * 状态，1有效，2无效
   */
  private Integer status;
  /**
   * 交易编号
   */
  private Long tradeId;
  /**
   * 产品对应的链接
   */
  private String productUrl;
  /**
   * 展示的类型，1文本，2图片，3视频
   */
  //private Integer displayType;
  /**
   * 展示的内容,如果是多个的话用分号分隔
   */
  private String displayContent;
}
