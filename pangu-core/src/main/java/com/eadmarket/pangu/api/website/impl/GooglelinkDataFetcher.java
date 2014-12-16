/**
 *
 */
package com.eadmarket.pangu.api.website.impl;

import com.google.common.collect.Lists;

import com.eadmarket.pangu.api.website.WebSiteDataDO;

import java.util.List;

/**
 * googlelink
 *
 * @author liuyongpo@gmail.com
 */
class GooglelinkDataFetcher extends AbstractDataFetcher {

  @Override
  protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
    String googleLink = "";
    if (htmlContent.contains("約有") && htmlContent.contains("項結果")) {
      String temp = htmlContent;
      int startIndex = temp.indexOf("約有");
      temp = temp.substring(startIndex);
      int endIndex = temp.indexOf("項結果");
      googleLink = temp.substring(0, endIndex).replaceAll("[^0-9]", "").trim();
    }
    googleLink = assignDefaultValueIfBlank(googleLink);
    return Lists.newArrayList(new WebSiteDataDO("google", "link", googleLink));
  }

}
