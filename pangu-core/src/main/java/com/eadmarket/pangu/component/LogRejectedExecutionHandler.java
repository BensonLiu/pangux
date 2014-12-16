package com.eadmarket.pangu.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by liu on 12/26/13.
 */
class LogRejectedExecutionHandler implements RejectedExecutionHandler {

  private final static Logger LOG = LoggerFactory.getLogger(LogRejectedExecutionHandler.class);

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    LOG.error("ThreadPool reject", r);
  }
}
