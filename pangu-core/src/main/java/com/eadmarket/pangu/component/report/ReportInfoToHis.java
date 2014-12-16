package com.eadmarket.pangu.component.report;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.report.ReportInfoDao;
import com.eadmarket.pangu.domain.ReportInfoDO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * report_info表数据迁移到report_info_his表
 *
 * @author liuyongpo@gmail.com
 */
public class ReportInfoToHis {

  private static final Logger LOG = LoggerFactory.getLogger(ReportInfoToHis.class);

  @Resource
  private ReportInfoDao reportInfoDao;

  public void execute() {
    Date minGmtCreate = DateUtils.addDays(new Date(), -15);

    LOG.warn(
        "begin to move report_info records whose gmt_create is less than {} into report_info_his",
        DateFormatUtils.format(minGmtCreate, "yyyy-MM-dd HH:mm:ss"));

    int totalNum = 0;
    while (true) {
      List<ReportInfoDO> reportInfoDOs;
      try {
        reportInfoDOs = reportInfoDao.getReportInfoDOsByGmtCreate(minGmtCreate, 50);
      } catch (DaoException ex) {
        LOG.error("failed to query reportInfo", ex);
        return;
      }
      if (CollectionUtils.isEmpty(reportInfoDOs)) {
        LOG.warn("move report_info records to report_info_his end, {} records moved", totalNum);
        return;
      }
      for (ReportInfoDO reportInfo : reportInfoDOs) {
        try {
          reportInfoDao.insertReportInfoHis(reportInfo);
          reportInfoDao.deleteReportInfoById(reportInfo.getId());
          totalNum++;
        } catch (DaoException ex) {
          LOG.error("failed to move {} to report_info_his", reportInfo);
        }
      }

      try {
        TimeUnit.MILLISECONDS.sleep(1000L);
      } catch (InterruptedException ex) {
        //just ignore this exception
      }

    }
  }
}
