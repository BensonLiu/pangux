package com.eadmarket.pangu.dao.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.eadmarket.pangu.domain.PositionDO.PositionStatus;

/**
 * @author liuyongpo@gmail.com
 */
@MappedJdbcTypes({JdbcType.TINYINT,JdbcType.INTEGER})
public class PositionStatusTypeHandler extends IEnumTypeHandler<PositionStatus> {

	@Override
	protected PositionStatus[] allCells() {
		return PositionStatus.values();
	}

}
