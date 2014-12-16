package com.eadmarket.pangu.api.website;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link IDataFetcher}管理器
 *
 * @author liuyongpo@gmail.com
 */
public class DataFetcherManager implements ApplicationContextAware, DisposableBean {

  private final static Logger LOG = LoggerFactory.getLogger(DataFetcherManager.class);
  /**
   * 先用Cache线程池，不设置大小
   */
  private final static ExecutorService
      THREAD_POOL
      =
      Executors.newCachedThreadPool(
          new ThreadFactoryBuilder().setNameFormat("DataFetcherManager-%d").build());
  private Map<String, IDataFetcher> fetcherName2Fetcher;
  private int fetcherCount;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext)
      throws BeansException {
    fetcherName2Fetcher = applicationContext.getBeansOfType(IDataFetcher.class);
    fetcherCount = fetcherName2Fetcher.size();
  }

  public List<WebSiteDataDO> fetchFor(final String url) {
    final List<WebSiteDataDO> list = Lists.newCopyOnWriteArrayList();

    long begin = System.currentTimeMillis();
    final CountDownLatch latch = new CountDownLatch(fetcherCount);
    for (final Entry<String, IDataFetcher> entry : fetcherName2Fetcher.entrySet()) {
      Runnable task = new Runnable() {
        @Override
        public void run() {
          try {
            List<WebSiteDataDO> siteDataDOs = entry.getValue().fetch(url);
            if (!siteDataDOs.isEmpty()) {
              list.addAll(siteDataDOs);
            }
          } catch (Exception ex) {
            LOG.error("fetchFor " + url, ex);
          } finally {
            latch.countDown();
          }
        }
      };
      THREAD_POOL.submit(task);
    }
    try {
      latch.await();
      long end = System.currentTimeMillis();
      LOG.warn("{} millis for {}", end - begin, url);
    } catch (InterruptedException ex) {
      LOG.error("CountDownLatch.await interrupted", ex);
    }
    return list;
  }

  @Override
  public void destroy() throws Exception {
    THREAD_POOL.shutdown();
  }

}
