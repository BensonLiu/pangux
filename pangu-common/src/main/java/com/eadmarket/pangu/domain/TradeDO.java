package com.eadmarket.pangu.domain;

import java.util.Date;

import com.eadmarket.pangu.common.IEnum;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 交易信息对象
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class TradeDO {
	/**
	 * 交易编号
	 */
	private Long id;
	/**
	 * 交易状态
	 */
	private TradeStatus status;
	/**
	 * 对应的广告位编号
	 */
	private Long positionId;
	/**
	 * 对应的产品
	 */
	private Long productId;
	/**
	 * 成交价格，以分为单位
	 */
	private Long price;
	/**
	 * 开始时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 买家编号
	 */
	private Long buyerId;
	/**
	 * 卖家编号
	 */
	private Long sellerId;
	/**
	 * 成交数量
	 */
	private Integer num;
	/**
	 * 交易进度
	 */
	//private Integer process;
	
	public Long getTotalFee() {
		return price * num;
	}
	
	public static enum TradeStatus implements IEnum {
		//NEW(1, "新建"),
		IMPLEMENTING(2, "执行中"),
		COMPLETED(3, "已完成"),
		;

		@Getter private final int code;
		
		@Getter private final String desc;
		
		private TradeStatus(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
