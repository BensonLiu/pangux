package com.eadmarket.pangu.manager.knowledge.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dao.knowledge.KnowledgeCommentDao;
import com.eadmarket.pangu.dao.knowledge.KnowledgeDao;
import com.eadmarket.pangu.domain.KnowledgeCommentDO;
import com.eadmarket.pangu.domain.KnowledgeDO;
import com.eadmarket.pangu.manager.knowledge.KnowledgeCommentManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liu on 4/4/14.
 */
class KnowledgeCommentManagerImpl implements KnowledgeCommentManager {

    @Resource private KnowledgeCommentDao knowledgeCommentDao;

    @Resource private KnowledgeDao knowledgeDao;

    @Override
    public Long commentKnowledge(String ip, Long knowledgeId, String comment) throws ManagerException {

        try {
            KnowledgeDO knowledgeDO = knowledgeDao.getKnowledgeById(knowledgeId);
            if (knowledgeDO == null) {
                return -1L;
            }
        } catch (DaoException ex) {
            throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "failed to get knowledge " + knowledgeId, ex);
        }

        KnowledgeCommentDO knowledgeCommentDO = new KnowledgeCommentDO();
        knowledgeCommentDO.setKnowledgeId(knowledgeId);
        knowledgeCommentDO.setComment(comment);
        knowledgeCommentDO.setSrcIp(ip);
        try {
            return knowledgeCommentDao.insertComment(knowledgeCommentDO);
        } catch (DaoException ex) {
            throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "failed to insert " + knowledgeCommentDO, ex);
        }
    }

    @Override
    public List<KnowledgeCommentDO> queryCommentByKnowledgeId(Long knowledgeId) throws ManagerException {
        try {
            return knowledgeCommentDao.queryCommentByKnowledgeId(knowledgeId);
        } catch (DaoException ex) {
            throw new ManagerException(ExceptionCode.SYSTEM_ERROR,
                    "failed to query comments by knowledgeId " + knowledgeId, ex);
        }
    }
}
