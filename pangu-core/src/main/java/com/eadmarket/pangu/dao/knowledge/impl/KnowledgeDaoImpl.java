package com.eadmarket.pangu.dao.knowledge.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.knowledge.KnowledgeDao;
import com.eadmarket.pangu.domain.KnowledgeDO;
import com.eadmarket.pangu.query.KnowledgeQuery;

import java.util.List;

/**
 * Created by liu on 3/27/14.
 */
class KnowledgeDaoImpl extends BaseDao implements KnowledgeDao {

  @Override
  public Long saveKnowledge(KnowledgeDO knowledgeDO) throws DaoException {
    insert("KnowledgeDao.saveKnowledge", knowledgeDO);
    return knowledgeDO.getId();
  }

  @Override
  public List<KnowledgeDO> queryByMinId(Query<KnowledgeQuery> query) throws DaoException {
    return selectList("KnowledgeDao.queryByMinId", query);
  }

  @Override
  public Long countAllKnowledge() throws DaoException {
    return selectOne("KnowledgeDao.countAllKnowledge");
  }

  @Override
  public KnowledgeDO getKnowledgeById(Long id) throws DaoException {
    return selectOne("KnowledgeDao.getKnowledgeById", id);
  }
}
