package com.eadmarket.pangu.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by liu on 3/27/14.
 */
@Data
public class LightKnowledgeDO {

    private final static String PIC_PREFIX = "http://www.eadmarket.com/img/edu_app/";

    private Long id;

    private String content;

    private Long category;

    private String imgUrl;

    private List<KnowledgeCommentDO> comments;

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
        return lightKnowledgeDO;
    }
}
