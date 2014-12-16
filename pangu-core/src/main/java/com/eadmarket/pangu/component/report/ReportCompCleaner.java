package com.eadmarket.pangu.component.report;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.report.ReportCompDao;

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
 * 定期清理report_comp表的数据
 *
 * @author liuyongpo@gmail.com
 */
public class ReportCompCleaner {

  private static final Logger LOG = LoggerFactory.getLogger(ReportCompCleaner.class);

  @Resource
  private ReportCompDao reportCompDao;

  public void execute() {
    Date expireDate = DateUtils.addDays(new Date(), -45);
    deleteReportCompBeforeSomeday(3, expireDate);

    expireDate = DateUtils.addDays(new Date(), -7);
    deleteReportCompBeforeSomeday(4, expireDate);
  }

  private void deleteReportCompBeforeSomeday(int timeType, Date expireDate) {

    LOG.warn(
        "begin to remove report_comp records whose time_value is less than {} and time_type is {} ",
        DateFormatUtils.format(expireDate, "yyyy-MM-dd HH:mm:ss"), timeType);

    int totalNum = 0;
    while (true) {
      List<Long> removableIds;
      try {
        removableIds = reportCompDao.getRemovableIds(timeType, expireDate, 100);
      } catch (DaoException ex) {
        LOG.error("timeType:" + timeType + ",timeValue:" + expireDate, ex);
        return;
      }

      if (CollectionUtils.isEmpty(removableIds)) {
        LOG.warn("delete expired report_comps end, {} report_comp records removed", totalNum);
        return;
      }

      for (Long reportCompId : removableIds) {
        try {
          reportCompDao.deleteReportCompById(reportCompId);
          totalNum++;
        } catch (DaoException ex) {
          LOG.error("failed to delete reportComp id:" + reportCompId, ex);
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
