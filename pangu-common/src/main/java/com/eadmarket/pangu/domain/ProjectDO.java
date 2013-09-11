package com.eadmarket.pangu.domain;

import java.util.Collections;
import java.util.List;

import com.eadmarket.pangu.common.IEnum;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * ��վ������ģ������ṩ���λ��ʵ��
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class ProjectDO {
	/**
	 * ��վ���
	 */
	private Long id;
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
	/**
	 * ״̬
	 */
	private ProjectStatus status;
	/**
	 * alexa����
	 */
	private Long alexa;
	/**
	 * ��վ�µĹ��λ
	 */
	private List<PositionDO> positions = Collections.emptyList();
	
	public static enum ProjectStatus implements IEnum {
		INVALIDE(0, "δ��֤"),
		NORMAL(1, "����֤"),
		DELETED(2, "��ɾ��"),
		;
		
		@Getter private final int code;
		@Getter private final String desc;
		
		private ProjectStatus(final int code, final String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
