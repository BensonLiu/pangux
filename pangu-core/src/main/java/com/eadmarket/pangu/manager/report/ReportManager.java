package com.eadmarket.pangu.manager.report;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.ReportDO;

/**
 * ���Ч��ͳ��ҵ�������
 * 
 * @author liuyongpo@gmail.com
 */
public interface ReportManager {
	ReportDO getByUserId() throws ManagerException;
}
