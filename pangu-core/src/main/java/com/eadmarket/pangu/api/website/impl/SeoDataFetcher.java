package com.eadmarket.pangu.api.website.impl;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

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

        List<WebSiteDataDO> govRecord = getGovRecord(result);

        if (CollectionUtils.isNotEmpty(govRecord)) {
            list.addAll(govRecord);
        }

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
        String brValue = "";
        if(htmlContent.contains("baiduapp/") && htmlContent.contains(".gif")) {
            String temp = htmlContent;
            int startIndex = temp.indexOf("baiduapp/");
            temp = temp.substring(startIndex);
            int endIndex = temp.indexOf(".gif");
            brValue = temp.substring(0,endIndex).replaceAll("[^0-9]", "").trim();
        }
        brValue = assignDefaultValueIfBlank(brValue);
        return new WebSiteDataDO("baidu", "br", brValue);
    }

    private WebSiteDataDO getGooglePR(String htmlContent) {
        String pr = "";
        if(htmlContent.contains("Rank_")&& htmlContent.contains(".gif")){
            String temp = htmlContent;
            int startIndex = temp.indexOf("Rank_");
            temp = temp.substring(startIndex);
            int endIndex = temp.indexOf(".gif");
            if(!temp.substring(0,endIndex).contains("-")){
                pr = temp.substring(0,endIndex).replaceAll("[^0-9]", "").trim();
            }
        }
        pr = assignDefaultValueIfBlank(pr);
        return new WebSiteDataDO("google", "pr", pr);
    }

    private WebSiteDataDO getBaiduFlow(String htmlContent) {
        String baiduFlow = "";
        if(htmlContent.contains("百度流量预计") && htmlContent.contains("blank\">")){
            String temp = htmlContent;
            int startIndex = temp.indexOf("百度流量预计");
            temp = temp.substring(startIndex);
            startIndex = temp.indexOf("blank\">");
            int endIndex = temp.indexOf("</a></td>");
            baiduFlow = temp.substring(startIndex+7,endIndex);
        }
        baiduFlow = assignDefaultValueIfBlank(baiduFlow);
        return new WebSiteDataDO("baidu", "flow", baiduFlow);
    }

    private WebSiteDataDO getBaiduKeyWord(String htmlContent) {
        String keyWord = "";
        if(htmlContent.contains("关键词库") && htmlContent.contains("blank\">")){
            String temp = htmlContent;
            int startIndex = temp.lastIndexOf("关键词库");
            temp = temp.substring(startIndex);
            startIndex = temp.indexOf("\">");
            int endIndex = temp.indexOf("</a></td>");
            keyWord = temp.substring(startIndex+2,endIndex);
        }
        keyWord = assignDefaultValueIfBlank(keyWord);
        return new WebSiteDataDO("baidu", "keyword", keyWord);
    }

    /**
     * 解析出来备案信息
     */
    private List<WebSiteDataDO> getGovRecord(String htmlContent) {
        String[] s = new String[4];
        try {
            int index = htmlContent.lastIndexOf("<tr class=\"seo_item\">\t\t\t\t<td title=\"域名备案\"");

            if (index < 0) {
                filledWithNegativeOne(s);
            } else {
                String tmpString = htmlContent.substring(index);

                index = tmpString.indexOf("</tr>");
                tmpString = tmpString.substring(0, index);

                index = tmpString.indexOf("<font color=#0269AC>备案号");
                tmpString = tmpString.substring(index);

                index = tmpString.indexOf("</td>");
                tmpString = tmpString.substring(0, index).trim();

                final String[] split = tmpString.split("&nbsp;&nbsp;");
                int i = 0;
                for (String str : split) {
                    index = str.indexOf("</font>");
                    str = str.substring(index);
                    s[i++] = str.replace("</font>", "").trim();
                }
            }
        } catch (Exception ex) {
            LOG.error("parse govRecord", ex);
            filledWithNegativeOne(s);
        }

        return generateWebsiteList(s);
    }

    private void filledWithNegativeOne(String[] s) {
        for (int i = 0; i < s.length; i ++) {
            s[i] = NEGATIVE_ONE;
        }
    }

    private List<WebSiteDataDO> generateWebsiteList(String[] str) {
        List<WebSiteDataDO> list = Lists.newArrayList();
        list.add(new WebSiteDataDO("govRecord", "num", str[0]));
        list.add(new WebSiteDataDO("govRecord", "type", str[1]));
        list.add(new WebSiteDataDO("govRecord", "name", str[2]));
        list.add(new WebSiteDataDO("govRecord", "auditDate", str[3]));
        return list;
    }
}
