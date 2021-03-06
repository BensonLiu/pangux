package com.eadmarket.pangu.domain;

import com.eadmarket.pangu.common.IEnum;

import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 网站啊神马的，可以提供广告位的实体
 *
 * @author liuyongpo@gmail.com
 */
@Data
@ToString
public class ProjectDO {

  /**
   * 网站编号
   */
  private Long id;
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
  /**
   * 状态
   */
  private ProjectStatus status;
  /**
   * alexa排名
   */
  private Long alexa;
  /**
   * 中国区排名
   */
  private Long localRank;
  /**
   * 网站下的广告位
   */
  private List<AdvertiseDO> positions = Collections.emptyList();

  public static enum ProjectStatus implements IEnum {
    INVALIDE(0, "未验证"),
    NORMAL(1, "已验证"),
    DELETED(2, "已删除"),;

    @Getter
    private final int code;
    @Getter
    private final String desc;

    private ProjectStatus(final int code, final String desc) {
      this.code = code;
      this.desc = desc;
    }
  }

}
