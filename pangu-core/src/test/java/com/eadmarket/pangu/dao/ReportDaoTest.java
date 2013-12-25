package com.eadmarket.pangu.dao;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.report.ReportDao;
import com.eadmarket.pangu.domain.ReportDO;
import com.eadmarket.pangu.query.ReportQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * TestCase for ReportDao
 *
 * @author liuyongpo@gmail.com
 */
public class ReportDaoTest extends BaseTest {
    @Resource private ReportDao reportDao;

    private Long reportId;

    @Before public void setUp() throws DaoException {
        reportId = insertReportAndReturnId();
    }

    @After public void tearDown() throws DaoException {
        if (reportId != null) {
            adJdbcTemplate.execute("delete from report where id = " + reportId);
        }
    }

    @Test public void testGetReportDOByIpAndPosition_no_exist() throws DaoException {
        ReportDO nullReport = getReportDO("127.0.0.1", 0L);

        assertThat(nullReport, is(nullValue()));
    }

    @Test public void testGetReportDOByIpAndPosition_exist() throws DaoException {
        ReportDO notNullReport = getReportDO("127.0.0.1", 1L);

        assertThat(notNullReport, is(notNullValue()));
    }

    private ReportDO getReportDO(String ip, Long positionId) throws DaoException {
        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setPositionId(positionId);
        reportQuery.setIp(ip);
        return reportDao.getReportDOByIpAndPosition(reportQuery);
    }

    @Test public void testUpdateReport() throws DaoException {
        ReportDO reportDO = new ReportDO();

        reportDO.setId(0L);
        reportDO.setClick(1L);
        reportDO.setImpression(1L);

        reportDao.updateReportImpAndClick(reportDO);
    }

    @Test public void testInsert() throws DaoException {

        Long reportId = 0L;
        try {
            reportId = insertReportAndReturnId();
            assertThat(reportId, is(notNullValue()));
            assertThat(reportId, is(greaterThan(0L)));
        } finally {
            if (reportId != null && reportId != 0L) {
                adJdbcTemplate.execute("delete from report where id = " + reportId);
            }
        }

    }

    private Long insertReportAndReturnId() throws DaoException {
        ReportDO reportDO = new ReportDO();

        reportDO.setPositionId(1L);
        reportDO.setIp("127.0.0.1");
        reportDO.setBuyerId(0L);
        reportDO.setSellerId(0L);
        reportDO.setProductId(0L);
        reportDO.setTradeId(0L);
        reportDO.setClick(1L);
        reportDO.setImpression(1L);

        return reportDao.insert(reportDO);
    }
}
