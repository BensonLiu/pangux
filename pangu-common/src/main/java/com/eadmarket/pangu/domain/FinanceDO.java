package com.eadmarket.pangu.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * ����ҵ�����
 * 
 * @author liuyongpo@gmail.com
 */
@Data@ToString
public class FinanceDO {
	private Long id;
	
	private Long userId;
	
	private Integer type;
	
	private Integer number;
	
	private Long balance;
	
	private String remark;
	
	private Date gmtCreate;
}
