package com.eadmarket.pangu.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 报表对象
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class ReportDO {
    /**
     * 记录编号
     */
	private Long id;
    /**
     * 广告位编号
     */
	private Long positionId;
    /**
     * 产品编号
     */
	private Long productId;
    /**
     * 交易编号
     */
	private Long tradeId;
	/**
	 * 展示次数
	 */
	private Long impression;
	/**
	 * 点击次数
	 */
	private Long click;
	/**
	 * 买家编号
	 */
	private Long buyerId;
	/**
	 * 卖家编号
	 */
	private Long sellerId;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
    /**
     * 访问来源ip
     */
	private String ip;
}
