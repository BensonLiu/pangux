package com.eadmarket.pangu.api.website.impl;

import com.eadmarket.pangu.api.website.IDataFetcher;
import com.eadmarket.pangu.api.website.WebSiteDataDO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import lombok.Setter;

abstract class AbstractDataFetcher implements IDataFetcher {

  protected final static Logger LOG = LoggerFactory.getLogger(AbstractDataFetcher.class);

  protected final static String NEGATIVE_ONE = "-1";

  /**
   * 数据请求地址
   */
  @Setter
  private String serverUrl;
  /**
   * 对应网站的编码
   */
  @Setter
  private String charset;

  @Override
  public List<WebSiteDataDO> fetch(String url) {

    String requestUrl = bindUrl(url);

    String htmlContent = getHTMLContent(requestUrl);

    return exactValueFromHtml(htmlContent);
  }

  /**
   * 拼接Url，子类可以覆盖扩展
   *
   * @param param 网站domain
   */
  protected String bindUrl(String param) {
    return serverUrl + param;
  }

  protected String getHTMLContent(String url) {

    InputStream inputStream = null;
    try {
      HttpURLConnection urlCon = (HttpURLConnection) new URL(url)
          .openConnection();
      urlCon.setConnectTimeout(30000);
      urlCon.setReadTimeout(30000);
      urlCon.setUseCaches(true);
      urlCon.setRequestMethod("GET");

			/*
			 * 伪装浏览器要装得像一点
			 */
      urlCon.setRequestProperty("User-Agent",
                                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.9; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
      urlCon.addRequestProperty("Referer", url);

      inputStream = urlCon.getInputStream();

      BufferedReader
          bufferedReader =
          new BufferedReader(new InputStreamReader(inputStream, charset));

      StringBuilder htmlContent = new StringBuilder("");

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        htmlContent.append(line);
      }
      return htmlContent.toString();
    } catch (Exception ex) {
      LOG.error("url:" + url, ex);
    } finally {
      try {
        if (inputStream != null) {
          inputStream.close();
        }
      } catch (IOException ex) {
        LOG.error("Failed to close inputStream to url:" + url, ex);
      }
    }
    return "";
  }

  abstract protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent);

  protected String assignDefaultValueIfBlank(String value) {
    if (StringUtils.isBlank(value)) {
      value = NEGATIVE_ONE;
    }
    return value;
  }

}
