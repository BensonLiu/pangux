package com.eadmarket.pangu.manager.knowledge.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.knowledge.KnowledgeDao;
import com.eadmarket.pangu.domain.KnowledgeDO;
import com.eadmarket.pangu.manager.knowledge.KnowledgeManager;
import com.eadmarket.pangu.query.KnowledgeQuery;
import com.eadmarket.pangu.util.seq.ISequenceGenerator;
import com.eadmarket.pangu.util.seq.SeqException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liu on 3/27/14.
 */
class KnowledgeManagerImpl implements KnowledgeManager {

    @Resource private KnowledgeDao knowledgeDao;

    @Resource private ISequenceGenerator sequenceGenerator;

    @Override
    public void saveKnowledge(KnowledgeDO knowledgeDO) throws ManagerException {
        try {
            Long category = knowledgeDO.getCategory();
            if (category == null) {
                throw new ManagerException(ExceptionCode.SYSTEM_ERROR);
            }

            Long id = sequenceGenerator.get("knowledge_cate_" + category);
            knowledgeDO.setId(id * 100 + category);
            knowledgeDao.saveKnowledge(knowledgeDO);
        } catch (DaoException ex) {
            throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
        } catch (SeqException ex) {
            throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
        }
    }

    @Override
    public List<KnowledgeDO> queryByMinId(Query<KnowledgeQuery> query) throws ManagerException {
        try {
            return knowledgeDao.queryByMinId(query);
        } catch (DaoException ex) {
            throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
        }
    }

    @Override
    public Long countAllKnowledge() throws ManagerException {
        try {
            return knowledgeDao.countAllKnowledge();
        } catch (DaoException ex) {
            throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
        }
    }
}
