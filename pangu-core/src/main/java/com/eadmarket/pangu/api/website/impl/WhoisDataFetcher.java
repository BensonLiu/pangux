package com.eadmarket.pangu.api.website.impl;

import com.alibaba.fastjson.JSON;
import com.eadmarket.pangu.api.website.WebSiteDataDO;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * whois查询
 *
 * @author renying@taobao.com
 */
class WhoisDataFetcher extends AbstractDataFetcher {

    @Override
    protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {
        Map<String, Object> responseJson = (Map<String, Object>) JSON.parse(htmlContent);

        final String success = (String) responseJson.get("success");
        List<WebSiteDataDO> list = Lists.newArrayList();
        if (!"1".equals(success)) {
            return list;
        }
        final Map<String, String> data = (Map<String, String>) responseJson.get("result");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            if ("details".equals(key)) {
                /*
                 * 解析detail字段，解析成元素
                 */
                List<WebSiteDataDO> siteDataDOs = generateWhoisDetailsWebsiteList(value);
                list.addAll(siteDataDOs);
                continue;
            }
            list.add(new WebSiteDataDO("whois", key, value));
        }
        return list;
    }

    private List<WebSiteDataDO> generateWhoisDetailsWebsiteList(String details) {
        String[] split = details.split("<br>");
        List<WebSiteDataDO> list = Lists.newArrayList();
        for (String kv : split) {
            if (StringUtils.isNotBlank(kv)) {
                int endIndex = kv.indexOf(":");
                String key = kv.substring(0, endIndex);
                String value = kv.substring(endIndex + 1, kv.length());
                list.add(new WebSiteDataDO("whois", key, value));
            }
        }
        return list;
    }

    @Override
    protected String bindUrl(String param) {
        return super.bindUrl(param) + "&app=domain.whois&appkey=10223&sign=355977b3bb0a1aae2f9f99a8d4e86d43&format=json";
    }
}
