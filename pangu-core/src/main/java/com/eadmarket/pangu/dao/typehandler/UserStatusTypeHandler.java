package com.eadmarket.pangu.dao.typehandler;

import com.eadmarket.pangu.domain.UserDO.UserStatus;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

/**
 * @author liuyongpo@gmail.com
 */
@MappedJdbcTypes({JdbcType.TINYINT, JdbcType.INTEGER})
public class UserStatusTypeHandler extends IEnumTypeHandler<UserStatus> {

  @Override
  protected UserStatus[] allCells() {
    return UserStatus.values();
  }

}
