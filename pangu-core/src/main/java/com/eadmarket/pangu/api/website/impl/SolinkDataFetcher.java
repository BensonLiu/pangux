/**
 * 
 */
package com.eadmarket.pangu.api.website.impl;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * solink
 * 
 * @author liuyongpo@gmail.com
 */
class SolinkDataFetcher extends AbstractDataFetcher {

	@Override
	protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
        String soLink = "";
        if(htmlContent.contains("找到相关结果约") && htmlContent.contains("个")){
            String temp = htmlContent;
            int startIndex = temp.lastIndexOf("找到相关结果约");
            temp = temp.substring(startIndex);
            int endIndex = temp.indexOf("个");
            soLink = temp.substring(0,endIndex).replaceAll("[^0-9]", "").trim();
        }
        soLink = assignDefaultValueIfBlank(soLink);
        return Lists.newArrayList(new WebSiteDataDO("so", "link", soLink));
	}

	@Override
	protected String bindUrl(String param) {
		return super.bindUrl(param) + "'";
	}
}
