package com.eadmarket.pangu.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.annotation.Resource;

import com.eadmarket.pangu.api.website.WebSiteDataDO;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.api.website.DataFetcherManager;
import com.eadmarket.pangu.api.website.IDataFetcher;

public class ApiTest extends BaseTest {

	@Resource private DataFetcherManager dataFetcherManager;
	
	@Resource private IDataFetcher alexaDataFetcher;
	
	@Resource private IDataFetcher baidusiteDataFetcher;
	
	@Test public void test() {
		List<WebSiteDataDO> siteDataDOs = dataFetcherManager.fetchFor("www.126.com");
		assertThat(siteDataDOs, notNullValue());
        assertThat(siteDataDOs.size(), greaterThanOrEqualTo(10));
		
		String jsonString = JSON.toJSONString(siteDataDOs);
		LOG.debug(jsonString);
	}
	
	@Test public void test2() {
		List<WebSiteDataDO> siteDataDOs = alexaDataFetcher.fetch("www.126.com");
		assertThat(siteDataDOs, notNullValue());
        assertThat(siteDataDOs.size(), equalTo(8));
	}
	
	@Test public void testBaidusite() {
		List<WebSiteDataDO> baidusite = baidusiteDataFetcher.fetch("www.126.com");
		assertThat(baidusite, notNullValue());
        assertThat(baidusite.isEmpty(), equalTo(false));
        assertThat(baidusite.size(), greaterThan(0));
    }

}
