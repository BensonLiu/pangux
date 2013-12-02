package com.eadmarket.pangu.api.website.impl;

import java.util.Collections;
import java.util.List;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;

class AlexaDataFetcher extends AbstractDataFetcher {

	private final static String ALEXA_CHINAZ_URL = "http://alexa.chinaz.com/Get_Data.asp";
	
	@Override
	protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
		List<WebSiteDataDO> list = Collections.emptyList();
		
		if(htmlContent.contains(ALEXA_CHINAZ_URL)&&htmlContent.contains("\">")){
            int startIndex = htmlContent.indexOf(ALEXA_CHINAZ_URL);
            htmlContent = htmlContent.substring(startIndex);
            int endIndex = htmlContent.indexOf("\">");
            String currentUrl = htmlContent.substring(0,endIndex);
            String url = currentUrl.replace("≈ ","");
            if(!url.contains("排名")) {
            	String value = getHTMLContent(url);
            	list = translateToList(value);
            }
        }
		
		return list;
	}
	
	private List<WebSiteDataDO> translateToList(String jsString) {
		jsString = jsString.replaceAll("document.getElementById\\('", "");
		jsString = jsString.replaceAll("'\\).innerHTML", "");
		jsString = jsString.replaceAll("'", "");
		String[] split = jsString.split(";");

        List<WebSiteDataDO> list = Lists.newArrayList();
		for (String str : split) {
			if (str == null || str.trim().isEmpty()) {
				continue;
			}
			String[] keyValue = str.split("=");
			list.add(new WebSiteDataDO("alexa", keyValue[0], keyValue[1]));
		}
		return list;
	}

}
