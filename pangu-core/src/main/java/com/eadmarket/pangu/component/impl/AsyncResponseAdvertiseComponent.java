package com.eadmarket.pangu.component.impl;

import com.eadmarket.pangu.component.ResponseAdvertiseComponent;
import com.eadmarket.pangu.domain.AdvertiseDO;

import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

/**
 * 响应广告位的浏览和点击事件，异步实现方式
 *
 * @author liuyongpo@gmail.com
 */
class AsyncResponseAdvertiseComponent implements ResponseAdvertiseComponent {

  @Resource
  private ExecutorService executorService;

  @Resource
  private SyncResponseAdvertiseComponent syncResponseAdvertiseComponent;

  @Override
  public void responseViewAdvertise(final AdvertiseDO advertiseDO, final String srcIp) {
    executorService.submit(new Runnable() {
      @Override
      public void run() {
        syncResponseAdvertiseComponent.responseViewAdvertise(advertiseDO, srcIp);
      }
    });
  }

  @Override
  public void responseClickAdvertise(final AdvertiseDO advertiseDO, final String srcIp) {
    executorService.submit(new Runnable() {
      @Override
      public void run() {
        syncResponseAdvertiseComponent.responseClickAdvertise(advertiseDO, srcIp);
      }
    });
  }
}
