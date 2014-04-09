package com.eadmarket.pangu.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by liu on 3/27/14.
 */
@Data
public class LightKnowledgeDO {

    private final static String PIC_PREFIX = "http://static.eadmarket.com/img/edu_app/";

    private Long id;

    private String content;

    private String imgUrl;

    private List<KnowledgeCommentDO> comments;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createDate;

    public static LightKnowledgeDO from(KnowledgeDO knowledgeDO) {
        LightKnowledgeDO lightKnowledgeDO = new LightKnowledgeDO();
        lightKnowledgeDO.setId(knowledgeDO.getId());
        lightKnowledgeDO.setContent(knowledgeDO.getSummary());
        String imgUrl = knowledgeDO.getImgUrl();
        if (StringUtils.isBlank(imgUrl)) {
            imgUrl = "";
        } else {
            imgUrl = PIC_PREFIX + imgUrl;
        }
        lightKnowledgeDO.setImgUrl(imgUrl);
        lightKnowledgeDO.setComments(knowledgeDO.getComments());
        lightKnowledgeDO.setCreateDate(knowledgeDO.getGmtCreate());
        return lightKnowledgeDO;
    }
}
