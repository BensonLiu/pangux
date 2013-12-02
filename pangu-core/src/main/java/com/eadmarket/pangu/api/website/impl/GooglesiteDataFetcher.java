/**
 * 
 */
package com.eadmarket.pangu.api.website.impl;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * googlesite
 * 
 * @author liuyongpo@gmail.com
 */
class GooglesiteDataFetcher extends AbstractDataFetcher {

	@Override
	protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
        List<WebSiteDataDO> list = Collections.emptyList();

		if(htmlContent.contains("約有") && htmlContent.contains("項結果")){
            String temp = htmlContent;
            int startIndex = temp.indexOf("約有");
            temp = temp.substring(startIndex);
            int endIndex = temp.indexOf("項結果");
            String value = temp.substring(0,endIndex).replaceAll("[^0-9]", "").trim();

            list = Lists.newArrayList(new WebSiteDataDO("google", "site", value));
		}
		return list;
	}

}
