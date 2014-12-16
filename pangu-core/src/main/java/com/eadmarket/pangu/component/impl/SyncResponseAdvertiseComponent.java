package com.eadmarket.pangu.component.impl;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.component.ResponseAdvertiseComponent;
import com.eadmarket.pangu.domain.AdvertiseDO;
import com.eadmarket.pangu.domain.ReportInfoDO;
import com.eadmarket.pangu.manager.position.AdvertiseManager;
import com.eadmarket.pangu.manager.report.ReportInfoManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 响应广告位的浏览和点击事件，同步实现方式
 *
 * @author liuyongpo@gmail.com
 */
class SyncResponseAdvertiseComponent implements ResponseAdvertiseComponent {

  private final static Logger LOG = LoggerFactory.getLogger(SyncResponseAdvertiseComponent.class);

  @Resource
  private AdvertiseManager advertiseManager;

  @Resource
  private ReportInfoManager reportInfoManager;

  @Override
  public void responseViewAdvertise(AdvertiseDO advertiseDO, String srcIp) {
    try {
      if (advertiseDO.isCanReserve()) {
        advertiseManager
            .updateAdvertiseStatus(advertiseDO.getId(), AdvertiseDO.AdvertiseStatus.ON_SALE);
      } else if (advertiseDO.isSoldOut()) {
        reportInfoManager.responseForOperation(advertiseDO, srcIp, ReportInfoDO.DISPLAY_OPT_TYPE);
      }
    } catch (ManagerException ex) {
      LOG.error("advertiseId:" + advertiseDO.getId() + ",ip:" + srcIp, ex);
    }
  }

  @Override
  public void responseClickAdvertise(AdvertiseDO advertiseDO, String srcIp) {
    try {
      reportInfoManager.responseForOperation(advertiseDO, srcIp, ReportInfoDO.CLICK_OPT_TYPE);
    } catch (ManagerException ex) {
      LOG.error("advertiseId:" + advertiseDO.getId() + ",ip:" + srcIp, ex);
    }
  }
}
