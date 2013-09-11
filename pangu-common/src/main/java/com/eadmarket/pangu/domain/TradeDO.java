package com.eadmarket.pangu.domain;

import java.util.Date;

import com.eadmarket.pangu.common.IEnum;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * ������Ϣ����
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class TradeDO {
	/**
	 * ���ױ��
	 */
	private Long id;
	/**
	 * ����״̬
	 */
	private TradeStatus status;
	/**
	 * ��Ӧ�Ĺ��λ���
	 */
	private Long positionId;
	/**
	 * ��Ӧ�Ĳ�Ʒ
	 */
	private Long productId;
	/**
	 * �ɽ��۸��Է�Ϊ��λ
	 */
	private Long price;
	/**
	 * ��ʼʱ��
	 */
	private Date startDate;
	/**
	 * ����ʱ��
	 */
	private Date endDate;
	/**
	 * ��ұ��
	 */
	private Long buyerId;
	/**
	 * ���ұ��
	 */
	private Long sellerId;
	/**
	 * �ɽ�����
	 */
	private Integer num;
	/**
	 * ���׽���
	 */
	//private Integer process;
	
	public Long getTotalFee() {
		return price * num;
	}
	
	public static enum TradeStatus implements IEnum {
		//NEW(1, "�½�"),
		IMPLEMENTING(2, "ִ����"),
		COMPLETED(3, "�����"),
		;

		@Getter private final int code;
		
		@Getter private final String desc;
		
		private TradeStatus(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
