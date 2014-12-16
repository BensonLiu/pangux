/**
 *
 */
package com.eadmarket.pangu.dao;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.knowledge.KnowledgeCommentDao;
import com.eadmarket.pangu.domain.KnowledgeCommentDO;

import org.junit.Test;

import java.util.List;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * TestCase for {@link com.eadmarket.pangu.dao.knowledge.KnowledgeCommentDao}
 *
 * @author liuyongpo@gmail.com
 */
public class KnowledgeCommentDaoTest extends BaseTest {

  @Resource
  private KnowledgeCommentDao knowledgeCommentDao;

  @Test
  public void testInsertComment() throws DaoException {

    KnowledgeCommentDO knowledgeCommentDO = new KnowledgeCommentDO();
    knowledgeCommentDO.setComment("Junit");
    knowledgeCommentDO.setKnowledgeId(10L);
    Long commentId = null;
    try {
      commentId = knowledgeCommentDao.insertComment(knowledgeCommentDO);
      assertThat(commentId, is(notNullValue()));
    } finally {
      if (commentId != null) {
        educationAppJdbcTemplate.execute("delete from knowledge_comment where id = " + commentId);
      }
    }

  }

  @Test
  public void testQueryCommentByKnowledgeId() throws DaoException {
    List<KnowledgeCommentDO>
        knowledgeCommentDOs =
        knowledgeCommentDao.queryCommentByKnowledgeId(14L);

    assertThat(knowledgeCommentDOs, is(notNullValue()));
    assertThat(knowledgeCommentDOs.size(), is(greaterThanOrEqualTo(0)));
  }

}
