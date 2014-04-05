/**
 * 
 */
package com.eadmarket.pangu.dao;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.knowledge.KnowledgeDao;
import com.eadmarket.pangu.dao.position.AdvertiseDao;
import com.eadmarket.pangu.domain.AdvertiseContractDO;
import com.eadmarket.pangu.domain.AdvertiseDO;
import com.eadmarket.pangu.domain.KnowledgeDO;
import com.eadmarket.pangu.query.KnowledgeQuery;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * TestCase for {@link com.eadmarket.pangu.dao.position.AdvertiseDao}
 * 
 * @author liuyongpo@gmail.com
 */
public class KnowledgeDaoTest extends BaseTest {

	@Resource private KnowledgeDao knowledgeDao;
	
	@Test public void testSaveKnowledge() throws DaoException {
        Long id = null;
        try {

            KnowledgeDO knowledgeDO = new KnowledgeDO();
            knowledgeDO.setCategory(1L);
            knowledgeDO.setSummary("How周");
            id = knowledgeDao.saveKnowledge(knowledgeDO);

            assertThat(id, notNullValue());
        } finally {
            if (id != null) {
                educationAppJdbcTemplate.execute("delete from knowledge_info where id = " + id);
            }
        }

	}

    @Test public void test_queryByMinId() throws DaoException {
        KnowledgeQuery knowledgeQuery = new KnowledgeQuery();
        //knowledgeQuery.setMaxKnowledgeId(10000L);
        knowledgeQuery.setMinKnowledgeId(1L);
        knowledgeQuery.setCategorys(Lists.newArrayList(1L));

        Query<KnowledgeQuery> query = Query.create(knowledgeQuery);

        query.setPageSize(100);

        List<KnowledgeDO> knowledgeDOs = knowledgeDao.queryByMinId(query);

        assertThat(knowledgeDOs, is(notNullValue()));
        assertThat(knowledgeDOs.size(), is(greaterThanOrEqualTo(0)));
    }

    @Test public void test_countAllKnowledge() throws DaoException {
        Long count = knowledgeDao.countAllKnowledge();
        assertThat(count, greaterThanOrEqualTo(0L));
    }

    @Test public void test_getKnowledgeById() throws DaoException {
        KnowledgeDO knowledgeDO = knowledgeDao.getKnowledgeById(10L);
        assertThat(knowledgeDO, anything());
    }

}
