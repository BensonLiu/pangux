package com.eadmarket.pangu.dao.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.eadmarket.pangu.domain.UserDO.UserStatus;

/**
 * @author liuyongpo@gmail.com
 */
@MappedJdbcTypes({JdbcType.TINYINT,JdbcType.INTEGER})
public class UserStatusTypeHandler extends IEnumTypeHandler<UserStatus> {

	@Override
	protected UserStatus[] allCells() {
		return UserStatus.values();
	}

}
