package com.eadmarket.pangu.dao.typehandler;

import com.eadmarket.pangu.domain.AdvertiseDO;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

/**
 * @author liuyongpo@gmail.com
 */
@MappedJdbcTypes({JdbcType.TINYINT,JdbcType.INTEGER})
public class AdvertiseStatusTypeHandler extends IEnumTypeHandler<AdvertiseDO.AdvertiseStatus> {

	@Override
	protected AdvertiseDO.AdvertiseStatus[] allCells() {
		return AdvertiseDO.AdvertiseStatus.values();
	}

}
