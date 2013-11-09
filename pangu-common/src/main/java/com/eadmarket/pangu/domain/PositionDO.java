package com.eadmarket.pangu.domain;

import com.eadmarket.pangu.common.IEnum;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 广告位对象
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class PositionDO {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 所属站点
	 */
	private Long projectId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 格式
	 */
	private Integer format;
	/**
	 * 宽度
	 */
	private Integer width;
	/**
	 * 高度
	 */
	private Integer height;
	/**
	 * 价格，以分为单位
	 */
	private Long price;
	/**
	 * 广告位状态
	 */
	private PositionStatus status;
	/**
	 * 广告收益，以分为单位
	 */
	private Long profit;
	
	private Long ownerId;
	
	/**
	 * 广告位对应的激活链接
	 */
	private String activeUrl;
	
	//private Integer process;
	
	public double getPriceYuan() {
		return (double)(price / 100);
	}
	
	public double getProfitYuan() {
		return (double)(profit / 100);
	}
	
	public boolean isOnSale() {
		return PositionStatus.ON_SALE == status;
	}
	
	public static enum PositionStatus implements IEnum {
		ON_SALE(0, "闲置中"),
		SOLD_OUT(1, "已卖出"),
		LOCKED(2, "已锁定"),
		;
		
		@Getter private final int code;
		@Getter private final String desc;
		
		private PositionStatus(final int code, final String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
