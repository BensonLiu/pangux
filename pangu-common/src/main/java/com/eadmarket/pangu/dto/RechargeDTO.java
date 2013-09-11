package com.eadmarket.pangu.dto;

import lombok.Data;
import lombok.ToString;

import com.eadmarket.pangu.domain.RechargeDO.ChannelType;

/**
 * Recharge数据传输对象
 * 
 * @author liuyongpo@gmail.com
 */
@Data @ToString
public class RechargeDTO {
	/**
	 * 会员编号
	 */
	private Long userId;
	/**
	 * 充值渠道
	 */
	private ChannelType channelType;
	/**
	 * 充值金额，以分为单位
	 */
	private Long cash;
}
