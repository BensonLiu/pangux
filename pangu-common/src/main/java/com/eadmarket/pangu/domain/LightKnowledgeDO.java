package com.eadmarket.pangu.domain;

import lombok.Data;

/**
 * Created by liu on 3/27/14.
 */
@Data
public class LightKnowledgeDO {
    private Long id;

    private String content;

    private Long category;

    private String imgUrl;

    public static LightKnowledgeDO from(KnowledgeDO knowledgeDO) {
        LightKnowledgeDO lightKnowledgeDO = new LightKnowledgeDO();
        lightKnowledgeDO.setId(knowledgeDO.getId());
        lightKnowledgeDO.setContent(knowledgeDO.getSummary());
        lightKnowledgeDO.setCategory(knowledgeDO.getCategory());
        lightKnowledgeDO.setImgUrl(knowledgeDO.getImgUrl());
        return lightKnowledgeDO;
    }
}
