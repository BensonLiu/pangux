package com.eadmarket.pangu.module.screen.api;

import com.google.common.collect.Maps;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.fastjson.JSON;
import com.eadmarket.pangu.api.website.DataFetcherManager;
import com.eadmarket.pangu.api.website.WebSiteDataDO;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 站长助手使用的数据服务
 *
 * @author liuyongpo@gmail.com
 */
public class WebsiteData {

  @Resource
  private DataFetcherManager dataFetcherManager;

  public void execute(TurbineRunData runData, Context context) {
    String requestUrl = runData.getParameters().getString("url");
    Map<String, Object> map = Maps.newHashMap();
    if (StringUtils.isBlank(requestUrl)) {
      map.put("success", "false");
    } else {
      map.put("success", "true");
      Map<String, Map<String, String>> data = getWebsiteDataMap(requestUrl);
      map.put("data", data);
    }

    String json = JSON.toJSONString(map);
    context.put("JSON", json);
  }

  private Map<String, Map<String, String>> getWebsiteDataMap(String requestUrl) {
    List<WebSiteDataDO> dataDOList = dataFetcherManager.fetchFor(requestUrl);
    Map<String, Map<String, String>> map = Collections.emptyMap();
    if (!dataDOList.isEmpty()) {
      map = Maps.newHashMap();
      for (WebSiteDataDO siteDataDO : dataDOList) {
        String group = siteDataDO.getGroup();
        Map<String, String> dataKey2value = map.get(group);
        if (dataKey2value == null) {
          dataKey2value = Maps.newHashMap();
        }
        dataKey2value.put(siteDataDO.getKeyName(), siteDataDO.getValue());
        map.put(group, dataKey2value);
      }
    }
    return map;
  }

}
