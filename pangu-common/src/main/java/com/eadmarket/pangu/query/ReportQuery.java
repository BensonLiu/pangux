/**
 * 
 */
package com.eadmarket.pangu.query;

import lombok.Data;

import java.util.Date;

/**
 * report查询对象
 * 
 * @author liuyongpo@gmail.com
 */
@Data
public final class ReportQuery {
    private String ip;

    private Long positionId;

    private Long buyerId;

    private Date startDate;

    private Date endDate;

    private Long tradeId;
}
