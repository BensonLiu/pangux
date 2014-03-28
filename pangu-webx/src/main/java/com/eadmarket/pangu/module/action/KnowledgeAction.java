package com.eadmarket.pangu.module.action;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.KnowledgeDO;
import com.eadmarket.pangu.manager.knowledge.KnowledgeManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 会员操作Action
 * 
 * @author liuyongpo@gmail.com
 */
public class KnowledgeAction {
	
	private final static Logger LOG = LoggerFactory.getLogger(KnowledgeAction.class);
	
	@Resource private KnowledgeManager knowledgeManager;
	
	public void doAdd(TurbineRunData runData, Context context){
		try {
			boolean formIsInvalid = false;
			Long category = runData.getParameters().getLong("category");

            context.put("category", category);

            String summary = runData.getParameters().getString("summary", "");
            if (StringUtils.isBlank(summary)) {
                context.put("error_message", "请填写内容");
                formIsInvalid = true;
            }

            context.put("summary", summary);

			if (formIsInvalid) {
				return;
			}

            final KnowledgeDO knowledgeDO = new KnowledgeDO();

            knowledgeDO.setSummary(summary);
            knowledgeDO.setCategory(category);

            knowledgeManager.saveKnowledge(knowledgeDO);
            context.put("error_message", "添加成功");
		} catch (ManagerException ex) {
			ExceptionCode code = ex.getCode();
			context.put("error_message", code.getDesc());
			if (code.isSystemError()) {
				LOG.error("addKnowledge " + code, ex);
			}
		}
	}

}
