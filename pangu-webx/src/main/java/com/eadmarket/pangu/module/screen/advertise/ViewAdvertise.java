package com.eadmarket.pangu.module.screen.advertise;

import com.google.common.collect.ImmutableMap;

import com.alibaba.citrus.service.uribroker.URIBrokerService;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.component.ResponseAdvertiseComponent;
import com.eadmarket.pangu.domain.AdvertiseContractDO;
import com.eadmarket.pangu.domain.AdvertiseDO;
import com.eadmarket.pangu.manager.position.AdvertiseManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import javax.annotation.Resource;

/**
 * 广告信息展示
 *
 * @author liuyongpo@gmail.com
 */
public class ViewAdvertise {

  private final static Logger LOG = LoggerFactory.getLogger(ViewAdvertise.class);
  private final static Map<Integer, String> TYPE_TO_URL
      = ImmutableMap.of(0, "mainTxtLink", 1, "mainImageLink", 2, "mainVideoLink");
  @Resource
  private AdvertiseManager advertiseManager;
  @Resource
  private ResponseAdvertiseComponent responseAdvertiseComponent;
  @Resource
  private URIBrokerService uriBrokerService;

  public void execute(TurbineRunData runData, Context context) {

    Long advertiseId = runData.getParameters().getLong("aid", -1);

    context.put("aid", advertiseId);

    if (advertiseId <= 0) {
      context.put("success", "false");
      return;
    }

        /*invalid_view有值则表示本次访问不算数，不需要发送View的事件*/
    String ivd_vw = runData.getParameters().getString("ivd_vw");

    boolean sendViewEvent = ivd_vw == null;

    try {
      AdvertiseDO advertiseDO = advertiseManager.getAdvertiseDOWithContractById(advertiseId);
      if (advertiseDO == null) {
        context.put("success", "false");
        return;
      }

      String remoteAddress = runData.getRequest().getRemoteAddr();

      if (advertiseDO.isOnSale() || advertiseDO.isCanReserve()) {
        String content = advertiseDO.getDefaultDisplayContent();
        extractAdvertiseVOAndSendViewEvent(context, advertiseDO, remoteAddress, content,
                                           sendViewEvent);
        return;
      }

      AdvertiseContractDO contractDO = advertiseDO.getContractDO();
      if (contractDO == null) {
        context.put("success", "false");
      } else {
        String
            content =
            generateAdvertiseDisplayUrl(advertiseDO.getFormat(), contractDO.getDisplayContent());
        extractAdvertiseVOAndSendViewEvent(context, advertiseDO, remoteAddress, content,
                                           sendViewEvent);
      }
    } catch (ManagerException ex) {
      context.put("success", "false");
      LOG.error("ViewAdvertise, advertiseId=" + advertiseId, ex);
    }
        /*
         * 设置格式，以备跨域请求需求
         */
    runData.getResponse().setContentType("text/javascript");
  }

  private void extractAdvertiseVOAndSendViewEvent(Context context, AdvertiseDO advertiseDO,
                                                  String remoteAddr,
                                                  String content, boolean sendEvent) {
    AdvertiseInfoVO advertiseInfoVO = copyPropertiesFrom(advertiseDO);

    if (StringUtils.isNotBlank(content)) {
      advertiseInfoVO.setContent(content);
    }
    context.put("advertiseInfoVO", advertiseInfoVO);
    context.put("success", "true");
    if (sendEvent) {
      responseAdvertiseComponent.responseViewAdvertise(advertiseDO, remoteAddr);
    }
  }

  /**
   * 根据不同的格式产生不同的内容
   *
   * @param type      广告位格式
   * @param adContent 广告内容
   */
  private String generateAdvertiseDisplayUrl(int type, String adContent) {

    if (AdvertiseDO.TEXT_FORMAT.equals(type)) {
      /**
       * 文字是直接入库的，不是存储在文件系统上的，略显不统一，以后可以全部走CDN
       */
      return adContent;
    }

    String link = TYPE_TO_URL.get(type);
    String linkUrl = uriBrokerService.getURIBroker(link).fork().render();
    return linkUrl + "/" + adContent;
  }

  private AdvertiseInfoVO copyPropertiesFrom(AdvertiseDO advertiseDO) {
    AdvertiseInfoVO advertiseInfoVO = new AdvertiseInfoVO();

    Long advertiseId = advertiseDO.getId();
    advertiseInfoVO.setClickUrl(rendClickUrl(advertiseId));
    advertiseInfoVO.setType(advertiseDO.getFormat());
    advertiseInfoVO.setWidth(advertiseDO.getWidth());
    advertiseInfoVO.setHeight(advertiseDO.getHeight());
    advertiseInfoVO.setStatus(advertiseDO.getStatus().getCode());

    return advertiseInfoVO;
  }

  /**
   * 根据广告位编号渲染出来一个点击链接
   *
   * @param advertiseId 广告位编号
   */
  private String rendClickUrl(Long advertiseId) {
    return uriBrokerService.getURIBroker("advertiseClick").fork().addQueryData("aid", advertiseId)
        .render();
  }
}
