package com.eadmarket.pangu.timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.position.PositionDao;
import com.eadmarket.pangu.domain.PositionDO;
import com.eadmarket.pangu.domain.PositionDO.PositionStatus;

/**
 * 广告位相关的时间程序
 * 
 * @author liuyongpo@gmail.com
 */
public final class PositionTimer {
	
	private final static Logger LOG = LoggerFactory.getLogger(PositionTimer.class);
	
	@Resource private PositionDao positionDao;
	
	public void execute() {
		/*
		 * 1.分页拉取处于0状态的广告位
		 * 
		 * 2.对每一个广告位，根据activeUrl判断当前广告位是否挂上了代码
		 * 
		 * 3.对于状态为0的，如果未检测到代码，直接把状态置为2
		 */
		Long minId = 0L;
		while(true) {
			try {
				List<PositionDO> positions = positionDao.queryPositionsForTimer(minId, PositionStatus.ON_SALE);
				if (CollectionUtils.isEmpty(positions)) {
					LOG.warn("finished all position on sale scan");
					return;
				}
				for (PositionDO position : positions) {
					minId = position.getId();
					String activeUrl = position.getActiveUrl();
					if (StringUtils.isBlank(activeUrl)) {
						LOG.warn("position({}) have a blank activeUrl", position.getId());
						continue;
					}
					if(!hasJsCodeOnActiveUrl(activeUrl)) {
						LOG.warn("position({}) have no active code on {} page", position.getId(), activeUrl);
						unactivePositionById(position.getId());
					}
				}
			} catch (DaoException ex) {
				LOG.error("minId:" + minId, ex);
			}
		}
	}

	private void unactivePositionById(Long id) {
		PositionDO param = new PositionDO();
		param.setId(id);
		param.setStatus(PositionStatus.LOCKED);
		try {
			positionDao.updatePositionById(param);
			LOG.warn("Position({})'status changed to {}", id, PositionStatus.LOCKED);
		} catch (DaoException ex) {
			LOG.error("failed to update position:" + id, ex);
		}
	}
	
	private static boolean hasJsCodeOnActiveUrl(String activeUrl) {
		HttpURLConnection urlCon;
		try {
			urlCon = (HttpURLConnection) new URL(activeUrl).openConnection();
			urlCon.setConnectTimeout(1000);
			urlCon.setReadTimeout(1000);
			urlCon.setUseCaches(false);
			urlCon.setRequestMethod("GET");
			int responseCode = urlCon.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				LOG.error("Failed to visit " + activeUrl + ", responseCode is " + responseCode);
				return true;
			}
			InputStream inputStream = urlCon.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String currentLine = "";
			while ((currentLine = bufferedReader.readLine()) != null) {
				if (currentLine.contains("<REACH RANK=")) {
					return true;
				}
			}
			bufferedReader.close();
			return false;
		} catch (IOException ex) {
			LOG.error("Failed to visit " + activeUrl, ex);
			return true;
		}
	}
	
}
