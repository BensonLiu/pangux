package com.eadmarket.pangu.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 创建交易上下文
 * 
 * @author liuyongpo@gmail.com
 *
 */
@Data@ToString
public class CreateTradeContext {
	/**
	 * 买家编号
	 */
	private Long buyerId;
	/**
	 * 广告位编号
	 */
	private Long positionId;
	/**
	 * 购买数量，以月为单位
	 */
	private int num;
	/**
	 * 成交金额，以分为单位
	 */
	private Long price;
	
	private Long productId;
}
