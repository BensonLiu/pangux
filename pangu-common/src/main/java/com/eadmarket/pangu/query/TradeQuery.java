package com.eadmarket.pangu.query;

import com.eadmarket.pangu.domain.TradeDO.TradeStatus;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 交易的查询
 *
 * @author liuyongpo@gmail.com
 */
@Data
@ToString
public class TradeQuery {

  private TradeStatus status;

  private Date maxEndDate;

  private Date minEndDate;

  private Long buyerId;

  private Long sellerId;
  /**
   * 上次划款时间
   */
  private Date lastTransferDate;
}
