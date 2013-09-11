package com.eadmarket.pangu.query;

import lombok.Data;
import lombok.ToString;

/**
 * 工程的查询对象
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class ProjectQuery {
	/**
	 * 所属类目编号
	 */
	private Long categoryId;
	/**
	 * 描述关键字
	 */
	private String descriKeyWord;
	
	/*
	public String getKeyWord() {
		return "%" + descriKeyWord + "%";
	}
	*/
}
