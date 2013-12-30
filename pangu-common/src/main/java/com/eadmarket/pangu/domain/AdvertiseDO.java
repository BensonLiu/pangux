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
@Data @ToString
public final class AdvertiseDO {

    public final static Integer TEXT_FORMAT = 0;

    public final static Integer IMAGE_FORMAT = 1;

    public final static Integer VIDEO_FORMAT = 2;

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
	private AdvertiseStatus status;
	/**
	 * 广告收益，以分为单位
	 */
	private Long profit;
    /**
     * 广告位的样式
     */
    private String style;
    /**
     * 默认展示内容
     */
    private String defaultDisplayContent;
    /**
     * 广告位对应的广告契约
     */
    private AdvertiseContractDO contractDO;
    /**
     * FIXME！以后才会需要
     */
	private Long ownerId;

    private String activeUrl;

	public double getPriceYuan() {
		return (double)(price / 100);
	}
	
	public double getProfitYuan() {
		return (double)(profit / 100);
	}
	
	public boolean isOnSale() {
		return AdvertiseStatus.ON_SALE == status;
	}

    public boolean isCanReserve() {
        return AdvertiseStatus.CAN_RESERVE == status;
    }

    public boolean isSoldOut() {
        return AdvertiseStatus.SOLD_OUT == status;
    }
	
	public static enum AdvertiseStatus implements IEnum {
		ON_SALE(0, "闲置中"),
		SOLD_OUT(1, "已卖出"),
		CAN_RESERVE(2, "可预订"),
		;
		
		@Getter private final int code;
		@Getter private final String desc;
		
		private AdvertiseStatus(final int code, final String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
