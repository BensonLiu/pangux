package com.eadmarket.pangu.component;

import com.eadmarket.pangu.domain.AdvertiseDO;

/**
 * @author liuyongpo@gmail.com
 */
public interface ResponseAdvertiseComponent {

  void responseViewAdvertise(AdvertiseDO advertiseDO, String srcIp);

  void responseClickAdvertise(AdvertiseDO advertiseDO, String srcIp);
}
