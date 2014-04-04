package com.eadmarket.pangu.dao.knowledge.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.knowledge.KnowledgeCommentDao;
import com.eadmarket.pangu.domain.KnowledgeCommentDO;

import java.util.List;

/**
 * Created by liu on 4/4/14.
 */
class KnowledgeCommentDaoImpl extends BaseDao implements KnowledgeCommentDao {

    @Override
    public Long insertComment(KnowledgeCommentDO knowledgeCommentDO) throws DaoException {
        insert("KnowledgeCommentDao.insertComment", knowledgeCommentDO);
        return knowledgeCommentDO.getId();
    }

    @Override
    public List<KnowledgeCommentDO> queryCommentByKnowledgeId(Long knowledgeId) throws DaoException {
        return selectList("KnowledgeCommentDao.queryByKnowledgeId", knowledgeId);
    }
}
