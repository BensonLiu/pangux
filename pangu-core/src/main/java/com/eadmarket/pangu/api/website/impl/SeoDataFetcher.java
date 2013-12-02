package com.eadmarket.pangu.api.website.impl;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 获取SEO数据
 * 
 * @author liuyongpo@gmail.com
 */
class SeoDataFetcher extends AbstractDataFetcher {

	@Override
	protected List<WebSiteDataDO> exactValueFromHtml(String result) {
		
		List<WebSiteDataDO> list = Lists.newArrayList();

        WebSiteDataDO baiduBR = getBaiduBR(result);
        if (baiduBR != null) {
            list.add(baiduBR);
        }

        WebSiteDataDO googlePR = getGooglePR(result);
        if (googlePR != null) {
            list.add(googlePR);
        }

        WebSiteDataDO baiduFlow = getBaiduFlow(result);
        if (baiduFlow != null) {
            list.add(baiduFlow);
        }

        WebSiteDataDO baiduKeyWord = getBaiduKeyWord(result);
        if (baiduKeyWord != null) {
            list.add(baiduKeyWord);
        }

        return list;
    }

    private WebSiteDataDO getBaiduBR(String htmlContent) {
        WebSiteDataDO siteDataDO = null;

        if(htmlContent.contains("baiduapp/") && htmlContent.contains(".gif")) {
            String temp = htmlContent;
            int startIndex = temp.indexOf("baiduapp/");
            temp = temp.substring(startIndex);
            int endIndex = temp.indexOf(".gif");
            String brValue = temp.substring(0,endIndex).replaceAll("[^0-9]", "").trim();

            siteDataDO = new WebSiteDataDO("baidu", "br", brValue);
        }
        return siteDataDO;
    }

    private WebSiteDataDO getGooglePR(String htmlContent) {
        WebSiteDataDO siteDataDO = null;
        if(htmlContent.contains("Rank_")&& htmlContent.contains(".gif")){
            String temp = htmlContent;
            int startIndex = temp.indexOf("Rank_");
            temp = temp.substring(startIndex);
            int endIndex = temp.indexOf(".gif");
            if(!temp.substring(0,endIndex).contains("-")){
                String pr = temp.substring(0,endIndex).replaceAll("[^0-9]", "").trim();
                siteDataDO = new WebSiteDataDO("google", "pr", pr);
            }
        }
        return siteDataDO;
    }

    private WebSiteDataDO getBaiduFlow(String htmlContent) {
        WebSiteDataDO siteDataDO = null;
        if(htmlContent.contains("百度流量预计") && htmlContent.contains("blank\">")){
            String temp = htmlContent;
            int startIndex = temp.indexOf("百度流量预计");
            temp = temp.substring(startIndex);
            startIndex = temp.indexOf("blank\">");
            int endIndex = temp.indexOf("</a></td>");
            String flow = temp.substring(startIndex+7,endIndex);
            siteDataDO = new WebSiteDataDO("baidu", "flow", flow);
        }
        return siteDataDO;
    }

    private WebSiteDataDO getBaiduKeyWord(String htmlContent) {
        WebSiteDataDO siteDataDO = null;
        if(htmlContent.contains("关键词库")&& htmlContent.contains("blank\">")){
            String temp = htmlContent;
            int startIndex = temp.lastIndexOf("关键词库");
            temp = temp.substring(startIndex);
            startIndex = temp.indexOf("\">");
            int endIndex = temp.indexOf("</a></td>");
            String keyWord = temp.substring(startIndex+2,endIndex);
            siteDataDO = new WebSiteDataDO("baidu", "keyword", keyWord);
        }
        return siteDataDO;
    }
}
