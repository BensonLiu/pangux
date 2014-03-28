package com.eadmarket.pangu.module.screen.knowledge;

import com.alibaba.citrus.turbine.Context;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.manager.knowledge.KnowledgeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by liu on 3/27/14.
 */
public class AddKnowledge {

    private final static Logger LOG = LoggerFactory.getLogger(AddKnowledge.class);

    @Resource private KnowledgeManager knowledgeManager;

    public void execute(Context context) {
        Long allKnowledge = 0L;
        try {
            allKnowledge = knowledgeManager.countAllKnowledge();
        } catch (ManagerException ex) {
            LOG.error("countAllKnowledge", ex);
        }
        context.put("knowledgeCount", allKnowledge);
    }
}
