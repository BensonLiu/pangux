package com.eadmarket.pangu.dao;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.report.ReportInfoDao;
import com.eadmarket.pangu.domain.ReportInfoDO;

import org.junit.Test;

import java.util.List;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * TestCase for ReportInfoDao
 *
 * @author liuyongpo@gmail.com
 */
public class ReportInfoDaoTest extends BaseTest {

  @Resource
  private ReportInfoDao reportInfoDao;

  @Test
  public void testInsert() throws DaoException {
    ReportInfoDO reportInfo = new ReportInfoDO();
    reportInfo.setAdvertiseId(1L);
    reportInfo.setTradeId(1L);
    reportInfo.setIp("127.0.0.1");
    reportInfo.setOperationType(ReportInfoDO.CLICK_OPT_TYPE);
    reportInfoDao.insertReportInfo(reportInfo);
  }

  @Test
  public void testGetReportInfoDOsByMinId() throws DaoException {
    List<ReportInfoDO> reportInfoDOs = reportInfoDao.getReportInfoDOsByMinId(0L, 10);
    assertThat(reportInfoDOs, is(notNullValue()));
    assertThat(reportInfoDOs.size(), is(greaterThanOrEqualTo(0)));
  }
}
