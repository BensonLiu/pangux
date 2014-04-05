package com.eadmarket.pangu.dao.knowledge;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.domain.KnowledgeDO;
import com.eadmarket.pangu.query.KnowledgeQuery;

import java.util.List;

/**
 * Created by liu on 3/27/14.
 */
public interface KnowledgeDao {
    /**
     * 存储知识接口
     *
     * @param knowledgeDO 知识对象
     * @throws DaoException
     */
    Long saveKnowledge(KnowledgeDO knowledgeDO) throws DaoException;

    /**
     * 根据最小编号查询
     *
     * @param query 查询对象
     * @return 满足查询条件的查询对象集合
     * @throws DaoException
     */
    List<KnowledgeDO> queryByMinId(Query<KnowledgeQuery> query) throws DaoException;

    /**
     * 查询当前所有知识的总数
     *
     * @return 当前知识的总数
     * @throws DaoException
     */
    Long countAllKnowledge() throws DaoException;

    /**
     * 根据知识编号获取只是详情
     *
     * @param id 知识编号
     * @return
     * @throws DaoException
     */
    KnowledgeDO getKnowledgeById(Long id) throws DaoException;
}
