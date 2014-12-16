package com.eadmarket.pangu.manager.report;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.AdvertiseDO;

/**
 * 广告效果统计业务操作类
 *
 * @author liuyongpo@gmail.com
 */
public interface ReportInfoManager {

  /**
   * 响应广告位的事件
   *
   * @param advertiseDO   广告位
   * @param ip            来源IP
   * @param operationType 操作类型
   */
  void responseForOperation(AdvertiseDO advertiseDO, String ip, int operationType)
      throws ManagerException;

}
