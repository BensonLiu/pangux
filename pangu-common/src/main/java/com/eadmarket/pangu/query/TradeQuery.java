package com.eadmarket.pangu.query;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

import com.eadmarket.pangu.domain.TradeDO.TradeStatus;

/**
 * ���׵Ĳ�ѯ
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class TradeQuery {
	private TradeStatus status;
	
	private Date endDate;
	
	private Long buyerId;
	
	private Long sellerId;
}
