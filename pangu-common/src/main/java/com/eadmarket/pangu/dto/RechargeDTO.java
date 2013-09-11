package com.eadmarket.pangu.dto;

import lombok.Data;
import lombok.ToString;

import com.eadmarket.pangu.domain.RechargeDO.ChannelType;

/**
 * Recharge���ݴ������
 * 
 * @author liuyongpo@gmail.com
 */
@Data @ToString
public class RechargeDTO {
	/**
	 * ��Ա���
	 */
	private Long userId;
	/**
	 * ��ֵ����
	 */
	private ChannelType channelType;
	/**
	 * ��ֵ���Է�Ϊ��λ
	 */
	private Long cash;
}
