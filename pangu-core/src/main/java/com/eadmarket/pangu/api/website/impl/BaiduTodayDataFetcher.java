/**
 * 
 */
package com.eadmarket.pangu.api.website.impl;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * baidu今日收录
 * 
 * @author liuyongpo@gmail.com
 */
class BaiduTodayDataFetcher extends AbstractDataFetcher {

	@Override
	protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
        List<WebSiteDataDO> siteDataDOs = Collections.emptyList();

		if(htmlContent.contains("找到相关结果数") && htmlContent.contains("个")){
            int startIndex = htmlContent.indexOf("找到相关结果数");
            htmlContent = htmlContent.substring(startIndex);
            int endIndex = htmlContent.indexOf("个");
            String value = htmlContent.substring(0,endIndex).replaceAll("[^0-9]", "").trim();

            siteDataDOs = Lists.newArrayList(new WebSiteDataDO("baidu", "today", value));
        }
		return siteDataDOs;
	}

	@Override
	protected String bindUrl(String param) {
		return super.bindUrl(param) + "&lm=1";
	}
	
}
