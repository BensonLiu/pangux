package com.eadmarket.pangu.dao.typehandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by liu on 3/28/14.
 */
public class KnowledgeImgUrlTypeHandler extends BaseTypeHandler<String> {

    private static final String IMG_HOST;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return addHostInfo(rs.getString(columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return addHostInfo(rs.getString(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return addHostInfo(cs.getString(columnIndex));
    }

    private static String addHostInfo(String path) {
        if (StringUtils.isNotBlank(path)) {
            path = IMG_HOST + path;
        }
        return path;
    }

    static {
        Properties properties = new Properties();
        ClassPathResource resource = new ClassPathResource("com/eadmarket/pangu/config/img_host.properties");
        try {
            properties.load(resource.getInputStream());
            IMG_HOST = properties.getProperty("imgHost");
            if (IMG_HOST == null) {
                throw new RuntimeException("no imgHost config in img_host.properties");
            }
        } catch (IOException ex) {
            throw new RuntimeException("failed to load img_host.properties", ex);
        }
    }
}
