package com.eadmarket.pangu.module.screen.advertise;

import com.alibaba.citrus.service.uribroker.URIBrokerService;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.component.ResponseAdvertiseComponent;
import com.eadmarket.pangu.domain.AdvertiseContractDO;
import com.eadmarket.pangu.domain.AdvertiseDO;
import com.eadmarket.pangu.manager.position.AdvertiseManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 广告点击的响应页面
 *
 * @author liuyongpo@gmail.com
 */
public class ClickAdvertise {

  private final static Logger LOG = LoggerFactory.getLogger(ClickAdvertise.class);

  @Resource
  private AdvertiseManager advertiseManager;

  @Resource
  private ResponseAdvertiseComponent responseAdvertiseComponent;

  @Resource
  private URIBrokerService uriBrokerService;

  private String mainUrl;

  public void execute(TurbineRunData runData, Navigator navigator) {
    long aid = runData.getParameters().getLong("aid", -1L);

    if (aid <= 0) {
      //跳转到eadmarket.com
      navigator.redirectToLocation(getMainUrl());
      return;
    }

    try {
      AdvertiseDO advertiseDO = advertiseManager.getAdvertiseDOWithContractById(aid);

      if (advertiseDO == null) {
        //跳转到eadmarket.com
        navigator.redirectToLocation(getMainUrl());
        return;
      }

      if (advertiseDO.isSoldOut()) {
        AdvertiseContractDO contractDO = advertiseDO.getContractDO();
        if (contractDO == null) {
          LOG.error("aid={} have no contract bind with it");
          return;
        }
        String productUrl = contractDO.getProductUrl();
        navigator.redirectToLocation(productUrl);
        String remoteAddress = runData.getRequest().getRemoteAddr();
        responseAdvertiseComponent.responseClickAdvertise(advertiseDO, remoteAddress);
      } else {
        navigator.redirectToLocation(getProjectListDetail(advertiseDO));
      }

    } catch (ManagerException ex) {
      LOG.error("failed to redirect for aid:" + aid, ex);
      navigator.redirectToLocation(getMainUrl());
    }
  }

  private String getMainUrl() {
    if (mainUrl == null) {
      mainUrl = uriBrokerService.getURIBroker("mainLink").fork().render();
    }
    return mainUrl;
  }

  private String getProjectListDetail(AdvertiseDO advertiseDO) {
    return uriBrokerService.getURIBroker("mainLink").fork().addQueryData("c", "main")
        .addQueryData("a", "detail")
        .addQueryData("project", advertiseDO.getProjectId()).render();
  }
}
