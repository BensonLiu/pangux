package com.eadmarket.pangu.util.seq.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import com.eadmarket.pangu.util.seq.ISequenceGenerator;
import com.eadmarket.pangu.util.seq.SeqException;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.sql.DataSource;

import lombok.Setter;

/**
 * Created by liu on 3/28/14.
 */
public class DatabaseBasedSequenceGenerator implements ISequenceGenerator {

  private final static String UPDATE_SQL_TEMPLATE
      = "update %s set seq_value = ?, gmt_modified = now() where seq_name = ? and seq_value = ?";

  private final static String SELECT_SQL_TEMPLATE
      = "select seq_value, step from %s where seq_name = ?";

  @Setter
  private String seqTableName;

  @Setter
  private DataSource dataSource;

  private String updateSql;

  private String selectSql;

  private LoadingCache<String, SeqCache>
      cache =
      CacheBuilder.newBuilder().build(new CacheLoader<String, SeqCache>() {
        @Override
        public SeqCache load(String key) throws Exception {
          SeqCache seqCache = new SeqCache();
          seqCache.seqName = key;
          seqCache.update();
          return seqCache;
        }
      });

  public void init() {
    if (StringUtils.isBlank(seqTableName)) {
      throw new IllegalArgumentException("seqTableName can't be blank");
    }
    if (dataSource == null) {
      throw new IllegalArgumentException("dataSource can't be null");
    }

    updateSql = String.format(UPDATE_SQL_TEMPLATE, seqTableName);
    selectSql = String.format(SELECT_SQL_TEMPLATE, seqTableName);
  }  @Override
  public Long get(String seqName) throws SeqException {
    try {
      SeqCache seqCache = cache.get(seqName);
      return seqCache.get();
    } catch (ExecutionException e) {
      throw new SeqException("failed to get for " + seqName, e);
    }
  }

  final class SeqCache {

    Long initialValue;

    Long maxValue;

    int step;

    String seqName;

    int cacheSize = 100;

    synchronized Long get() throws SeqException {
      while (true) {
        if (initialValue + step < maxValue) {
          initialValue += step;
          return initialValue;
        }
        update();
      }
    }

    private void update() throws SeqException {
      Connection connection = null;
      PreparedStatement selectPrepareStatement = null;
      PreparedStatement updatePrepareStatement = null;
      try {
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        int retryTimes = 10;
        while (retryTimes-- > 0) {
          selectPrepareStatement = connection.prepareStatement(selectSql);
          selectPrepareStatement.setString(1, seqName);

          ResultSet resultSet = selectPrepareStatement.executeQuery();

          if (resultSet.next()) {
            initialValue = resultSet.getLong("seq_value");
            step = resultSet.getInt("step");
          } else {
            throw new SeqException(seqName + " is not config in table " + seqTableName);
          }

          updatePrepareStatement = connection.prepareStatement(updateSql);
          updatePrepareStatement.setLong(1, initialValue + cacheSize * step);
          updatePrepareStatement.setString(2, seqName);
          updatePrepareStatement.setLong(3, initialValue);
          int count = updatePrepareStatement.executeUpdate();

          if (count <= 0) {
            continue;
          }

          selectPrepareStatement = connection.prepareStatement(selectSql);
          selectPrepareStatement.setString(1, seqName);

          resultSet = selectPrepareStatement.executeQuery();

          if (resultSet.next()) {
            maxValue = resultSet.getLong("seq_value");
            step = resultSet.getInt("step");

            connection.commit();
            return;
          }
        }

        if (retryTimes < 0) {
          throw new SeqException("retry update seq " + seqName + "up to 10 times");
        }

        connection.rollback();
      } catch (SQLException e) {
        try {
          connection.rollback();
        } catch (SQLException ex) {
          throw new SeqException("failed to rollback", ex);
        }
        throw new SeqException("failed to update seq " + seqName, e);
      } finally {

        if (selectPrepareStatement != null) {
          try {
            selectPrepareStatement.close();
          } catch (SQLException e) {
            throw new SeqException("failed to close connection", e);
          }
        }

        if (updatePrepareStatement != null) {
          try {
            updatePrepareStatement.close();
          } catch (SQLException e) {
            throw new SeqException("failed to close connection", e);
          }
        }

        if (connection != null) {
          try {
            connection.setAutoCommit(true);
            connection.close();
          } catch (SQLException e) {
            throw new SeqException("failed to close connection", e);
          }
        }
      }
    }
  }  @Override
  public List<Long> getMulti(String seqName, Integer num) throws SeqException {
    List<Long> idList = Lists.newArrayList();

    for (int i = 0; i < num; i++) {
      idList.add(get(seqName));
    }
    return idList;
  }





}
