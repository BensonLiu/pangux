package com.eadmarket.pangu.dto;

import lombok.Data;
import lombok.ToString;

/**
 * ��������������
 * 
 * @author liuyongpo@gmail.com
 *
 */
@Data@ToString
public class CreateTradeContext {
	/**
	 * ��ұ��
	 */
	private Long buyerId;
	/**
	 * ���λ���
	 */
	private Long positionId;
	/**
	 * ��������������Ϊ��λ
	 */
	private int num;
	/**
	 * �ɽ����Է�Ϊ��λ
	 */
	private Long price;
	
	private Long productId;
}
