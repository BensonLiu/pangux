package com.eadmarket.pangu.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.position.PositionDao;
import com.eadmarket.pangu.dao.project.ProjectDao;
import com.eadmarket.pangu.domain.ProjectDO;
import com.eadmarket.pangu.query.ProjectQuery;
import com.eadmarket.pangu.timer.WebsiteAlexaUpdator;

/**
 * @author liuyongpo@gmail.com
 */
public class ProjectDaoTest extends BaseTest {
	
	@Resource private ProjectDao projectDao;
	
	@Resource private PositionDao positionDao;
	
	@Resource private WebsiteAlexaUpdator websiteAlexaUpdator;
	
	@Test public void testCount() throws DaoException {
		ProjectQuery projectQuery = new ProjectQuery();
		projectQuery.setDescriKeyWord("人气");
		//projectQuery.setCategoryId(12L);
		
		Query<ProjectQuery> query = Query.create(projectQuery);
		
		int count = projectDao.count(query);
		
		assertThat(count, is(greaterThan(1)));
	}
	
	@Test public void testQuery() throws DaoException {
		ProjectQuery projectQuery = new ProjectQuery();
		projectQuery.setDescriKeyWord("人气");
		//projectQuery.setCategoryId(12L);
		
		Query<ProjectQuery> query = Query.create(projectQuery);
		query.setCurrentPage(1);
		query.setPageSize(2);
		query.setTotalItem(6);
		
		List<ProjectDO> list = projectDao.query(query);
		
		assertThat(list, is(notNullValue()));
		assertThat(list.size(), is(greaterThan(1)));
	}
	
}
