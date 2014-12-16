package com.eadmarket.pangu.manager.knowledge;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.KnowledgeCommentDO;

import java.util.List;

/**
 * Created by liu on 4/4/14.
 */
public interface KnowledgeCommentManager {

  Long commentKnowledge(String ip, Long knowledgeId, String comment) throws ManagerException;

  List<KnowledgeCommentDO> queryCommentByKnowledgeId(Long knowledgeId) throws ManagerException;
}
