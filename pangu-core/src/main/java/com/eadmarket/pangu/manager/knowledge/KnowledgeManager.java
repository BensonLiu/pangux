package com.eadmarket.pangu.manager.knowledge;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.domain.KnowledgeDO;
import com.eadmarket.pangu.query.KnowledgeQuery;

import java.util.List;

/**
 * Created by liu on 3/27/14.
 */
public interface KnowledgeManager {

    void saveKnowledge(KnowledgeDO knowledgeDO) throws ManagerException;

    List<KnowledgeDO> queryByMinId(Query<KnowledgeQuery> query) throws ManagerException;

    Long countAllKnowledge() throws ManagerException;
}
