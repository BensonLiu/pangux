package com.eadmarket.pangu.timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;

import lombok.ToString;

import org.apache.commons.lang3.StringUtils;
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
	 * 1.如果获取到了alexa排名指并且和数据库不同，那么更新数据中的alexa
	 * 2.如果获取到了china排名指并且和数据库不同，那么更新数据中的localRank
	 */
	private void updateWebsiteRankValueIfNecessary(ProjectDO project) {
		LOG.warn("attempt to update project {}", project);
		
		RankValuePair rankValuePair = getWebsiteAlexaRankValue(project.getUrl());
		
		LOG.warn("Got rankValuePair value is {}", rankValuePair);
		
		if (!rankValuePair.success 
				|| (rankValuePair.alexaRankValue.equals(project.getAlexa()) && rankValuePair.chinaRankValue.equals(project.getLocalRank()))
				|| (rankValuePair.alexaRankValue.equals(0L) && rankValuePair.chinaRankValue.equals(0L))) {
			return ;
		}
		LOG.warn("update project {} alexa to {}", project, rankValuePair);
		ProjectDO param = new ProjectDO();
		param.setId(project.getId());
		if (!rankValuePair.alexaRankValue.equals(0L)) {
			param.setAlexa(rankValuePair.alexaRankValue);
		}
		if (!rankValuePair.chinaRankValue.equals(0L)) {
			param.setLocalRank(rankValuePair.chinaRankValue);
		}
		try {
			projectDao.updateById(param);
		} catch (DaoException ex) {
			LOG.error("Failed to update alexa value for " + project.getUrl(), ex);
		}
	}
	
	private static RankValuePair getWebsiteAlexaRankValue(String websiteDomain) {
		RankValuePair pair = new RankValuePair();
		try {
			String alexaQuery = "http://data.alexa.com/data?cli=10&dat=snbamz&url=" + websiteDomain;
			HttpURLConnection urlCon = (HttpURLConnection) new URL(alexaQuery).openConnection();
			urlCon.setConnectTimeout(1000);
			urlCon.setReadTimeout(1000);
			urlCon.setUseCaches(false);
			urlCon.setRequestMethod("GET");
			int responseCode = urlCon.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				LOG.error("connect to " + websiteDomain + " got " + responseCode);
				pair.success = false;
				return pair;
			}
			InputStream inputStream = urlCon.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String currentLine = "";
			while ((currentLine = bufferedReader.readLine()) != null) {
				if (currentLine.contains("<POPULARITY ")) {
					
					int index = currentLine.indexOf("TEXT=");
					currentLine = currentLine.substring(index);
					
					currentLine = currentLine.replaceAll("[^0-9]", "").trim();
					Long alexaRankValue = 0L;
					if (StringUtils.isNotBlank(currentLine)) {
						alexaRankValue = Long.valueOf(currentLine);
					}
					pair.alexaRankValue = alexaRankValue;
				} else if (currentLine.contains("<COUNTRY CODE=" + '"' + "CN" + '"'+ " NAME=" + '"' + "China")) {
					currentLine = currentLine.replaceAll("[^0-9]", "").trim();
					Long chinaRankValue = 0L;
					if (StringUtils.isNotBlank(currentLine)) {
						chinaRankValue = Long.valueOf(currentLine);
					}
					pair.chinaRankValue = chinaRankValue;
				}
			}
			bufferedReader.close();
		} catch (IOException ex) {
			LOG.error("Failed to connect alexa for alexa value of " + websiteDomain, ex);
		}
		return pair;
	}
	
	@ToString
	private static class RankValuePair {
		boolean success = true;
		Long alexaRankValue = 0L;
		Long chinaRankValue = 0L;
	} 
	
	public static void main(String[] args) {
		RankValuePair websiteAlexaRankValue = getWebsiteAlexaRankValue("http://www.jandou.com/");
		System.out.println(websiteAlexaRankValue);
	}
}
