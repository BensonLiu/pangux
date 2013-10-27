package com.eadmarket.pangu.timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.project.ProjectDao;
import com.eadmarket.pangu.domain.ProjectDO;
import com.eadmarket.pangu.query.ProjectQuery;

/**
 * 更新网站的alexa排名
 * 
 * @author liuyongpo@gmail.com
 */
public final class WebsiteAlexaUpdator {
	
	private final static Logger LOG = LoggerFactory.getLogger(WebsiteAlexaUpdator.class);
	
	@Resource private ProjectDao projectDao;
	
	public void execute() {
		LOG.warn("begin to update website alexa rank value");
		
		Query<ProjectQuery> query = Query.create(new ProjectQuery());
		query.setPageSize(30);
		try {
			Integer totalNum = projectDao.count(query);
			if (totalNum <= 0) {
				LOG.warn("Got 0 projects, return directly");
				return;
			}
			query.setTotalItem(totalNum);
		} catch (DaoException ex) {
			LOG.error("Failed to count projects", ex);
			return;
		}
		int pageNum = 1;
		do {
			query.setCurrentPage(pageNum);
			try {
				List<ProjectDO> projects = projectDao.query(query);
				for (ProjectDO project : projects) {
					updateWebsiteRankValueIfNecessary(project);
				}
			} catch (DaoException ex) {
				LOG.error("Failed to query project", ex);
			}
			pageNum ++;
		} while (!query.isLastPage());
		
		LOG.warn("update alexa value end");
	}

	/**
	 * 如果获取到了alexa排名指并且和数据库不同，那么更新数据中的alexa
	 */
	private void updateWebsiteRankValueIfNecessary(ProjectDO project) {
		Long alexaValue = getWebsiteAlexaRankValue(project.getUrl());
		
		if (alexaValue == null || alexaValue <= 0 || alexaValue.equals(project.getAlexa())) {
			return ;
		}
		
		ProjectDO param = new ProjectDO();
		param.setId(project.getId());
		param.setAlexa(alexaValue);
		try {
			projectDao.updateById(param);
		} catch (DaoException ex) {
			LOG.error("Failed to update alexa value for " + project.getUrl(), ex);
		}
	}
	
	private Long getWebsiteAlexaRankValue(String websiteDomain) {
		try {
			String alexaQuery = "http://data.alexa.com/data/ezdy01DOo100QI?cli=10&url=" + websiteDomain;
			HttpURLConnection urlCon = (HttpURLConnection) new URL(alexaQuery).openConnection();
			urlCon.setConnectTimeout(1000);
			urlCon.setReadTimeout(1000);
			urlCon.setUseCaches(false);
			urlCon.setRequestMethod("GET");
			int responseCode = urlCon.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				LOG.error("connect to " + websiteDomain + " got " + responseCode);
				return 0L;
			}
			InputStream inputStream = urlCon.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String currentLine = "";
			while ((currentLine = bufferedReader.readLine()) != null) {
				if (currentLine.contains("<REACH RANK=")) {
					currentLine = currentLine.replaceAll("[^0-9]", "").trim();
					return Long.valueOf(currentLine);
				}
			}
			bufferedReader.close();
		} catch (IOException ex) {
			LOG.error("Failed to connect alexa for alexa value of " + websiteDomain, ex);
		}
		return 0L;
	}

}