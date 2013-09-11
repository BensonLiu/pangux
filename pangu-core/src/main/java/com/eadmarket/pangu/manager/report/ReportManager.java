package com.eadmarket.pangu.manager.report;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.ReportDO;

/**
 * 广告效果统计业务操作类
 * 
 * @author liuyongpo@gmail.com
 */
public interface ReportManager {
	ReportDO getByUserId() throws ManagerException;
}
