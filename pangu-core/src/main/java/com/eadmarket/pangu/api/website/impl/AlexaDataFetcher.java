package com.eadmarket.pangu.api.website.impl;

import java.util.Collections;
import java.util.List;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

class AlexaDataFetcher extends AbstractDataFetcher {

	private final static String ALEXA_CHINAZ_URL = "http://alexa.chinaz.com/Get_Data.asp";
	
	@Override
	protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
		List<WebSiteDataDO> list = Lists.newArrayList();
		
		if(htmlContent.contains(ALEXA_CHINAZ_URL)&&htmlContent.contains("\">")){
            int startIndex = htmlContent.indexOf(ALEXA_CHINAZ_URL);
            htmlContent = htmlContent.substring(startIndex);
            int endIndex = htmlContent.indexOf("\">");
            String currentUrl = htmlContent.substring(0,endIndex);
            String url = currentUrl.replace("≈ ","");
            if(!url.contains("排名")) {
            	String value = getHTMLContent(url);
                list.addAll(translateToList(value));
            }
        }

        if (!list.isEmpty()) {
            list.add(new WebSiteDataDO("alexa", "success", "true"));
        } else {
            list.add(new WebSiteDataDO("alexa", "success", "false"));
        }

        return list;
	}

    /**
     * 把多个document.getElementById('rankName').innerHTML='rankValue';
     * 转化成{@link com.eadmarket.pangu.api.website.WebSiteDataDO}列表
     */
	private List<WebSiteDataDO> translateToList(String jsString) {
		jsString = jsString.replaceAll("document.getElementById\\('", "");
		jsString = jsString.replaceAll("'\\).innerHTML", "");
		jsString = jsString.replaceAll("'", "");
		String[] split = jsString.split(";");

        List<WebSiteDataDO> list = Lists.newArrayList();
		for (String str : split) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			String[] keyValue = str.split("=");
			list.add(new WebSiteDataDO("alexa", keyValue[0], keyValue[1]));
		}
		return list;
	}

}
