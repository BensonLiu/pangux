/**
 * 
 */
package com.eadmarket.pangu.api.website;

import lombok.Data;

/**
 * 网站信息对象
 * 
 * @author liuyongpo@gmail.com
 */
@Data
public final class WebSiteDataDO {
	/**
	 * 数据所在的组名称
	 */
	private final String group;
	/**
	 * 数据名称
	 */
	private final String keyName;
	/**
	 * 数据值
	 */
	private final String value;
}
