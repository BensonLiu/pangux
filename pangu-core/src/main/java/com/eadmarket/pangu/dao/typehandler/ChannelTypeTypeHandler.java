package com.eadmarket.pangu.dao.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.eadmarket.pangu.domain.RechargeDO.ChannelType;

/**
 * @author liuyongpo@gmail.com
 *
 */
@MappedJdbcTypes({JdbcType.TINYINT,JdbcType.INTEGER})
public class ChannelTypeTypeHandler extends IEnumTypeHandler<ChannelType> {

	@Override
	protected ChannelType[] allCells() {
		return ChannelType.values();
	}

}
