package com.eadmarket.pangu.query;

import lombok.Data;
import lombok.ToString;

/**
 * ���̵Ĳ�ѯ����
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class ProjectQuery {
	/**
	 * ������Ŀ���
	 */
	private Long categoryId;
	/**
	 * �����ؼ���
	 */
	private String descriKeyWord;
	
	/*
	public String getKeyWord() {
		return "%" + descriKeyWord + "%";
	}
	*/
}
