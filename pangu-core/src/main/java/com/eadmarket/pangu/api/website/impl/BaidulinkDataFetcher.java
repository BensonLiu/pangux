/**
 * 
 */
package com.eadmarket.pangu.api.website.impl;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * baiduLink
 * 
 * @author liuyongpo@gmail.com
 */
class BaidulinkDataFetcher extends AbstractDataFetcher {

	@Override
	protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
        List<WebSiteDataDO> list = Collections.emptyList();

		if(htmlContent.contains("百度为您找到相关结果约") && htmlContent.contains("个")){
            String temp = htmlContent;
            int startIndex = temp.indexOf("百度为您找到相关结果约");
            temp = temp.substring(startIndex);
            int endIndex = temp.indexOf("个");
            String value = temp.substring(0,endIndex).replaceAll("[^0-9]", "").trim();

            list = Lists.newArrayList(new WebSiteDataDO("baidu", "site", value));
        }
		return list;
	}

}
