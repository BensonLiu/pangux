package com.eadmarket.pangu.api.website.impl;

import java.util.List;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;

/**
 * 获取百度快照时间
 * 
 * @author liuyongpo@gmail.com
 */
class BaidusiteDataFetcher extends AbstractDataFetcher {

	@Override
	protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
		
		List<WebSiteDataDO> list = Lists.newArrayList();
		
		WebSiteDataDO websiteDataDO = resolveBaiduSite(htmlContent);
		if (websiteDataDO != null) {
			list.add(websiteDataDO);
		}
		
		websiteDataDO = resolveBaiduPhoto(htmlContent);
		if (websiteDataDO != null) {
			list.add(websiteDataDO);
		}
		
		return list;
	}
	
	private WebSiteDataDO resolveBaiduSite(String htmlContent) {
        WebSiteDataDO websiteDateDO = null;
        if(htmlContent.contains("找到相关结果数")&&htmlContent.contains("个")){
            String temp = htmlContent;
            int startIndex = temp.indexOf("找到相关结果数");
            temp = temp.substring(startIndex);
            int endIndex = temp.indexOf("个");
            String baidu_site = temp.substring(0,endIndex).replaceAll("[^0-9]", "").trim();
            
            websiteDateDO = new WebSiteDataDO("baidu", "site", baidu_site);
        }
        return websiteDateDO;
    }
	
	private WebSiteDataDO resolveBaiduPhoto(String htmlContent) {
        WebSiteDataDO websiteDateDO = null;
        if(htmlContent.contains("<span class = \"g\">")&&htmlContent.contains("</span>")){
            String temp = htmlContent;
            int startIndex = temp.indexOf("<span class = \"g\">");
            temp = temp.substring(startIndex);
            int endIndex = temp.indexOf("</span>");
            temp = temp.substring(0, endIndex);
            String photo = temp.split("&nbsp;")[1];
            
            websiteDateDO = new WebSiteDataDO("baidu", "photo", photo);
        }
        return websiteDateDO;
    }

}
