/**
 *
 */
package com.eadmarket.pangu.api.website.impl;

import com.google.common.collect.Lists;

import com.eadmarket.pangu.api.website.WebSiteDataDO;

import java.util.List;

/**
 * baidu今日收录
 *
 * @author liuyongpo@gmail.com
 */
class BaiduTodayDataFetcher extends AbstractDataFetcher {

  @Override
  protected String bindUrl(String param) {
    return super.bindUrl(param) + "&lm=1";
  }

  @Override
  protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
    String baiduToday = "";
    if (htmlContent.contains("找到相关结果数") && htmlContent.contains("个")) {
      int startIndex = htmlContent.indexOf("找到相关结果数");
      htmlContent = htmlContent.substring(startIndex);
      int endIndex = htmlContent.indexOf("个");
      baiduToday = htmlContent.substring(0, endIndex).replaceAll("[^0-9]", "").trim();
    }
    baiduToday = assignDefaultValueIfBlank(baiduToday);
    return Lists.newArrayList(new WebSiteDataDO("baidu", "today", baiduToday));
  }

}
