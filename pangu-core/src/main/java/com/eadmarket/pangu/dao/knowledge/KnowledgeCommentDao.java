package com.eadmarket.pangu.dao.knowledge;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.KnowledgeCommentDO;

import java.util.List;

/**
 * Created by liu on 4/4/14.
 */
public interface KnowledgeCommentDao {

    /**
     * 插入新的评论，并返回评论的编号
     *
     * @param knowledgeCommentDO 评论对象
     * @return 数据库中评论的编号
     * @throws DaoException
     */
    Long insertComment(KnowledgeCommentDO knowledgeCommentDO) throws DaoException;

    /**
     * 查询属于某个知识下的有效评论
     *
     * @param knowledgeId 知识编号
     * @return 该知识下的评论集合，不分页
     * @throws DaoException
     */
    List<KnowledgeCommentDO> queryCommentByKnowledgeId(Long knowledgeId) throws DaoException;
}
