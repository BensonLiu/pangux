package com.eadmarket.pangu.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * �������
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class ReportDO {
	private Long id;
	
	private Long positionId;
	
	private Long productId;
	
	private Long tradeId;
	/**
	 * չʾ����
	 */
	private Long impression;
	/**
	 * �������
	 */
	private Long click;
	/**
	 * ��ұ��
	 */
	private Long buyerId;
	/**
	 * ���ұ��
	 */
	private Long sellerId;
	/**
	 * ����ʱ��
	 */
	private Date gmtCreate;
	
	private String ip;
}
