package com.eadmarket.pangu.module.action;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.KnowledgeDO;
import com.eadmarket.pangu.manager.knowledge.KnowledgeManager;
import com.eadmarket.pangu.util.FileUploadUtil;
import org.apache.commons.fileupload.FileItem;
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

    private final static int MAX_FILE_SIZE_BYTES = 1024 * 1024 * 5;

	@Resource private KnowledgeManager knowledgeManager;

    @Resource private FileUploadUtil fileUploadUtil;
	
	public void doAdd(TurbineRunData runData, Context context){
		try {
			boolean formIsInvalid = false;
			/*
			Long category = runData.getParameters().getLong("category");
            context.put("category", category);
            */

            String summary = runData.getParameters().getString("summary", "");

            if (StringUtils.isNotBlank(summary) && summary.contains("\"")){
                context.put("error_message", "内容存在双引号，不能添加");
                formIsInvalid = true;
            }
            context.put("summary", summary);

            FileItem fileItem = runData.getParameters().getFileItem("upFile");
            String url = null;
            if (fileItem != null) {
                if (fileItem.getSize() > MAX_FILE_SIZE_BYTES) {
                    context.put("error_message", "图片体积不能超过5M");
                    formIsInvalid = true;
                } else {
                    url = fileUploadUtil.uploadFile(fileItem);
                }
            }

            if (formIsInvalid) {
				return;
			}

            final KnowledgeDO knowledgeDO = new KnowledgeDO();

            knowledgeDO.setSummary(summary);
            knowledgeDO.setCategory(14L);
            knowledgeDO.setImgUrl(url);

            knowledgeManager.saveKnowledge(knowledgeDO);
            context.put("error_message", "添加成功");
		} catch (ManagerException ex) {
			ExceptionCode code = ex.getCode();
			context.put("error_message", code.getDesc());
			if (code.isSystemError()) {
				LOG.error("addKnowledge " + code, ex);
			}
		} catch (Exception ex) {
            context.put("error_message", "系统异常，请稍候重试");
            LOG.error("failed to add knowledge", ex);
        }
	}

}
