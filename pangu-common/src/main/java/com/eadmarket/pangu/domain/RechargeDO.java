package com.eadmarket.pangu.domain;

import java.util.Date;

import com.eadmarket.pangu.common.IEnum;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * ��ֵ��¼
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class RechargeDO {
	/**
	 * ��ֵ��¼���
	 */
	private Long id;
	/**
	 * ��Ա���
	 */
	private Long userId;
	/**
	 * ��ֵ��¼״̬
	 */
	private RechargeStatus status;
	/**
	 * ��ֵ���Է�Ϊ��λ
	 */
	private Long cash;
	/**
	 * ��ֵ����
	 */
	private ChannelType channelType;
	/**
	 * �ⲿ��ţ������û�����
	 */
	private Long outOrderId;
	/**
	 * ��¼����ʱ��
	 */
	private Date gmtCreate;
	
	public boolean isNew() {
		return status == RechargeStatus.NEW;
	}
	
	public boolean isCompleted() {
		return status == RechargeStatus.COMPELETED;
	}
	
	public static enum ChannelType implements IEnum {
		ALIPAY(101, "֧����"),
		CFT(102, "�Ƹ�ͨ"),
		;
		
		@Getter private final int code;
		
		@Getter private final String desc;
		
		private ChannelType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		
	}
	
	public static enum RechargeStatus implements IEnum {
		NEW(1, "������"),
		COMPELETED(2, "�����"),
		;

		@Getter private final int code;
		
		@Getter private final String desc;
		
		private RechargeStatus(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		
	}
}
