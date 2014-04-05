package com.eadmarket.pangu.query;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by liu on 3/27/14.
 */
@Data @ToString
public class KnowledgeQuery {
    /**
     * 最小的知识编号
     */
    private Long minKnowledgeId;
    /**
     * 类别集合
     */
    private List<Long> categorys;
    /**
     * 最大的知识编号
     */
    private Long maxKnowledgeId;
}
