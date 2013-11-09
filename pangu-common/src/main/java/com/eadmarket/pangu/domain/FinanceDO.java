package com.eadmarket.pangu.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 财务业务对象
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class FinanceDO {
	private Long id;
	
	private Long userId;
	
	private String type;
	
	private Long number;
	
	private String remark;
	
	private Date gmtCreate;
	
	/**
	 * 广告收入
	 */
	public final static String TYPE_AD_IN = "00";
	/**
	 * 广告支出
	 */
	public final static String TYPE_AD_OUT = "10";
	/**
	 * 充值
	 */
	public final static String TYPE_RECHARGE = "01";
	/**
	 * 提现
	 */
	public final static String TYPE_WITHDRAW = "11";
}
