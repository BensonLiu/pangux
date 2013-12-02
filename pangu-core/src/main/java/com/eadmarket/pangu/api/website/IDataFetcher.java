package com.eadmarket.pangu.api.website;

import java.util.List;

public interface IDataFetcher {
	
	List<WebSiteDataDO> fetch(String url);
}
