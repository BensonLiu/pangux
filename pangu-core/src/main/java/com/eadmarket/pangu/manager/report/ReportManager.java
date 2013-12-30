package com.eadmarket.pangu.manager.report;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.AdvertiseDO;

/**
 * 广告效果统计业务操作类
 * 
 * @author liuyongpo@gmail.com
 *
 */
public interface ReportManager {

    /**
     * 响应广告位的展示事件
     *
     * @param advertiseDO 广告位
     * @param ip 来源IP
     */
    void responseForDisplay(AdvertiseDO advertiseDO, String ip) throws ManagerException;

    /**
     * 响应广告位的点击事件
     *
     * @param advertiseDO 广告位
     * @param ip 来源IP
     */
    void responseForClick(AdvertiseDO advertiseDO, String ip) throws ManagerException;

}
