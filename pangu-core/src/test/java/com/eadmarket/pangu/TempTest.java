package com.eadmarket.pangu;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by liu on 1/10/14.
 */
public class TempTest {

  @Test
  public void testDateUtilsTruncate() {
    Date truncate = DateUtils.truncate(new Date(), Calendar.HOUR_OF_DAY);
    String format = DateFormatUtils.ISO_DATETIME_FORMAT.format(truncate);
    System.out.println(format);

    truncate = DateUtils.truncate(new Date(), Calendar.MONTH);
    format = DateFormatUtils.ISO_DATETIME_FORMAT.format(truncate);
    System.out.println(format);

    truncate = DateUtils.truncate(new Date(), Calendar.DATE);
    format = DateFormatUtils.ISO_DATETIME_FORMAT.format(truncate);
    System.out.println(format);

  }
}
