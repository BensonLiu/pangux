package com.eadmarket.pangu;

import com.eadmarket.pangu.component.report.ReportConverter;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by liu on 1/13/14.
 */
public class ReportConverterTest extends BaseTest {

    @Resource private ReportConverter reportConverter;

    @Test public void test() throws DaoException {
        reportConverter.convert();
    }

}
