package com.eadmarket.pangu.dto;

import lombok.Data;
import lombok.ToString;

/**
 * ����Project��������
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class CreateProjectContext {
	/**
	 * ����
	 */
	private String title;
	/**
	 * ��վ����
	 */
	private String url;
	/**
	 * ��վ����
	 */
	private String description;
	/**
	 * logoͼ��url
	 */
	private String logoUrl;
	/**
	 * ����Ŀ¼
	 */
	private Long categoryId;
	/**
	 * ���ͣ�0������վ��1������
	 */
	private Integer type;
	/**
	 * ����˭
	 */
	private Long ownerId;
}
