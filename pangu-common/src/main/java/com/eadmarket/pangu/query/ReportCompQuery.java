package com.eadmarket.pangu.query;

import lombok.Data;

import java.util.Date;

/**
 * Created by liu on 1/10/14.
 */
@Data
public class ReportCompQuery {

    private Long tradeId;

    private Integer timeType;

    private Date timeValue;

    private Long id;

    private Long clickNum;

    private Long displayNum;

    private Long productId;

    private Long advertiseId;
}
