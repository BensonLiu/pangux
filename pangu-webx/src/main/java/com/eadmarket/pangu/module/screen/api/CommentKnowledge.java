package com.eadmarket.pangu.module.screen.api;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.fastjson.JSON;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.manager.knowledge.KnowledgeCommentManager;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by liu on 4/4/14.
 */
public class CommentKnowledge {

    private final static Logger LOG = LoggerFactory.getLogger(CommentKnowledge.class);

    @Resource private KnowledgeCommentManager knowledgeCommentManager;

    public void execute(TurbineRunData runData, Context context) {
        Map<String, Object> result = Maps.newHashMap();

        Long knowledgeId = runData.getParameters().getLong("k_id", 0);
        if (knowledgeId <= 0) {
            result.put("success", 0);
            String json = JSON.toJSONString(result);
            context.put("JSON", json);
            return;
        }

        String comment = runData.getParameters().getString("cmt");
        if (StringUtils.isBlank(comment)) {
            result.put("success", 0);
            String json = JSON.toJSONString(result);
            context.put("JSON", json);
            return;
        }

        int success = 1;
        try {
            String ip = runData.getRequest().getRemoteAddr();
            knowledgeCommentManager.commentKnowledge(ip, knowledgeId, comment);
        } catch (ManagerException ex) {
            LOG.error("failed to comment knowledge " + knowledgeId, ex);
            success = 0;
        }
        result.put("success", success);
        String json = JSON.toJSONString(result);
        context.put("JSON", json);
    }
}
