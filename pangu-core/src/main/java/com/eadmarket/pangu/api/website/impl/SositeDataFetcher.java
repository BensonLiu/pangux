/**
 *
 */
package com.eadmarket.pangu.api.website.impl;

import com.google.common.collect.Lists;

import com.eadmarket.pangu.api.website.WebSiteDataDO;

import java.util.List;

/**
 * sosite
 *
 * @author liuyongpo@gmail.com
 */
class SositeDataFetcher extends AbstractDataFetcher {

  @Override
  protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
    String soSite = "";
    if (htmlContent.contains("找到相关结果约") && htmlContent.contains("个")) {
      String temp = htmlContent;
      int startIndex = temp.lastIndexOf("找到相关结果约");
      temp = temp.substring(startIndex);
      int endIndex = temp.indexOf("个");
      soSite = temp.substring(0, endIndex).replaceAll("[^0-9]", "").trim();
    }
    soSite = assignDefaultValueIfBlank(soSite);
    return Lists.newArrayList(new WebSiteDataDO("so", "site", soSite));
  }

}
