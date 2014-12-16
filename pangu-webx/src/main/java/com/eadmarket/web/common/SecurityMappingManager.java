package com.eadmarket.web.common;

import java.util.Collections;
import java.util.Set;

import lombok.Setter;

/**
 * 路径拦截匹配管理
 *
 * @author liuyongpo@gmail.com
 */
public class SecurityMappingManager {

  @Setter
  private Set<String> unprotectedUrls = Collections.emptySet();

  /**
   * 判断链接是否是不需要登录
   *
   * @param url 请求的目标链接
   * @return true 如果不需要登录
   */
  public boolean isUnprotected(String url) {
    return unprotectedUrls.contains(url);
  }
}
