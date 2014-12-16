package com.eadmarket.pangu.domain;

import lombok.Data;
import lombok.ToString;

/**
 * 要推广的产品
 *
 * @author liuyongpo@gmail.com
 */
@Data
@ToString
public class ProductDO {

  public final static int DELETE_STATUS = 2;

  public final static int NORMAL_STATUS = 1;

  /**
   * 编号
   */
  private Long id;
  /**
   * 名称
   */
  private String name;
  /**
   * 状态
   */
  private Integer status;
  /**
   * 产品对应的链接
   */
  private String url;
  /**
   * 展示次数
   */
  private Long impression;
  /**
   * 点击次数
   */
  private Long click;
  /**
   * 格式
   */
  private Integer format;
  /**
   * 广告主
   */
  private Long ownerId;
  /**
   * 要展示的信息url(图片orflash)
   */
  private String showUrl;
  /**
   * 费用
   */
  private Long fee;
}
