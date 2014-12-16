package com.eadmarket.pangu.dao;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.report.ReportCompDao;
import com.eadmarket.pangu.query.ReportCompQuery;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by liu on 1/13/14.
 */
public class ReportCompDaoTest extends BaseTest {

  @Resource
  private ReportCompDao reportCompDao;

  @Test
  public void testUpdateReportCompById() throws DaoException {
    ReportCompQuery reportCompQueryParam = new ReportCompQuery();
    reportCompQueryParam.setId(116L);
    reportCompQueryParam.setClickNum(1L);
    reportCompQueryParam.setDisplayNum(1L);

    reportCompDao.updateReportCompById(reportCompQueryParam);
  }

  @Test
  public void testGetRemovableIds() throws DaoException {
    List<Long> removableIds = reportCompDao.getRemovableIds(3, new Date(), 5);
    assertThat(removableIds, is(notNullValue()));
    assertThat(removableIds.size(), is(greaterThanOrEqualTo(0)));
  }

}
