/**
 *
 */
package com.eadmarket.pangu.dao.typehandler;

import com.eadmarket.pangu.common.IEnum;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author liuyongpo@gmail.com
 */
@MappedJdbcTypes({JdbcType.TINYINT, JdbcType.INTEGER})
public abstract class IEnumTypeHandler<E extends IEnum> extends BaseTypeHandler<E> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, E parameter,
                                  JdbcType jdbcType) throws SQLException {
    ps.setInt(i, parameter.getCode());
  }

  @Override
  public E getNullableResult(ResultSet rs, String columnName)
      throws SQLException {
    int code = rs.getInt(columnName);
    return findByCode(code);
  }

  @Override
  public E getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    int code = rs.getInt(columnIndex);
    return findByCode(code);
  }

  @Override
  public E getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    int code = cs.getInt(columnIndex);
    return findByCode(code);
  }

  private E findByCode(int code) {
    E[] allCells = allCells();
    for (E e : allCells) {
      if (code == e.getCode()) {
        return e;
      }
    }
    return null;
  }

  abstract protected E[] allCells();
}
