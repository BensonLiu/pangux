package com.eadmarket.pangu.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 创建Project的上下文
 *
 * @author liuyongpo@gmail.com
 */
@Data
@ToString
public class CreateProjectContext {

  /**
   * 标题
   */
  private String title;
  /**
   * 网站链接
   */
  private String url;
  /**
   * 网站描述
   */
  private String description;
  /**
   * logo图标url
   */
  private String logoUrl;
  /**
   * 所属目录
   */
  private Long categoryId;
  /**
   * 类型，0代表网站，1代表博客
   */
  private Integer type;
  /**
   * 属于谁
   */
  private Long ownerId;
}
