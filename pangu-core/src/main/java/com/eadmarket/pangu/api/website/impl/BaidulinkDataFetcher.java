/**
 *
 */
package com.eadmarket.pangu.api.website.impl;

import com.google.common.collect.Lists;

import com.eadmarket.pangu.api.website.WebSiteDataDO;

import java.util.List;

/**
 * baiduLink
 *
 * @author liuyongpo@gmail.com
 */
class BaidulinkDataFetcher extends AbstractDataFetcher {

  @Override
  protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {

    String baiduLink = "";
    if (htmlContent.contains("百度为您找到相关结果约") && htmlContent.contains("个")) {
      String temp = htmlContent;
      int startIndex = temp.indexOf("百度为您找到相关结果约");
      temp = temp.substring(startIndex);
      int endIndex = temp.indexOf("个");
      baiduLink = temp.substring(0, endIndex).replaceAll("[^0-9]", "").trim();
    }
    baiduLink = assignDefaultValueIfBlank(baiduLink);
    return Lists.newArrayList(new WebSiteDataDO("baidu", "link", baiduLink));
  }

}
