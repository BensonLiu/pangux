package com.eadmarket.pangu.dao.typehandler;

import com.eadmarket.pangu.domain.RechargeDO.RechargeStatus;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

/**
 * @author liuyongpo@gmail.com
 */
@MappedJdbcTypes({JdbcType.TINYINT, JdbcType.INTEGER})
public class RechargeStatusTypeHandler extends IEnumTypeHandler<RechargeStatus> {

  @Override
  protected RechargeStatus[] allCells() {
    return RechargeStatus.values();
  }

}
