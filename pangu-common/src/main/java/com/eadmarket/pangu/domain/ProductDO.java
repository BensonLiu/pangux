package com.eadmarket.pangu.domain;

import lombok.Data;
import lombok.ToString;

/**
 * Ҫ�ƹ�Ĳ�Ʒ
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class ProductDO {
	
	public final static int DELETE_STATUS = 2;
	
	public final static int NORMAL_STATUS = 1;
	
	/**
	 * ���
	 */
	private Long id;
	/**
	 * ����
	 */
	private String name;
	/**
	 * ״̬
	 */
	private Integer status;
	/**
	 * ��Ʒ��Ӧ������
	 */
	private String url;
	/**
	 * չʾ����
	 */
	private Long impression;
	/**
	 * �������
	 */
	private Long click;
	/**
	 * ��ʽ
	 */
	private Integer format;
	/**
	 * �����
	 */
	private Long ownerId;
	/**
	 * Ҫչʾ����Ϣurl(ͼƬorflash)
	 */
	private String showUrl;
	/**
	 * ����
	 */
	private Long fee;
}
