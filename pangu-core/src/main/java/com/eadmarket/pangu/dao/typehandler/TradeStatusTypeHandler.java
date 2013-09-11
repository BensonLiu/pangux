package com.eadmarket.pangu.dao.typehandler;

import com.eadmarket.pangu.domain.TradeDO.TradeStatus;

/**
 * @author liuyongpo@gmail.com
 */
public class TradeStatusTypeHandler extends IEnumTypeHandler<TradeStatus> {

	@Override
	protected TradeStatus[] allCells() {
		return TradeStatus.values();
	}

}
