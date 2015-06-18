package com.eadmarket.pangu.dao;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.project.ProjectDao;
import com.eadmarket.pangu.domain.ProjectDO;
import com.eadmarket.pangu.query.ProjectQuery;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ProjectDaoTest extends BaseTest {

  @Resource
  private ProjectDao projectDao;

  @Test
  public void testCount() throws DaoException {
    ProjectQuery projectQuery = new ProjectQuery();
    projectQuery.setDescriKeyWord("人气");

    Query<ProjectQuery> query = Query.create(projectQuery);

    int count = projectDao.count(query);

    assertThat(count, is(greaterThan(1)));
  }

  @Test
  public void testQuery() throws DaoException {
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

  @Test
  public void testUpdate() throws DaoException {
    long id = 1L;

    ProjectDO projectDO = new ProjectDO();
    long alexa = Long.valueOf(new SimpleDateFormat("yymmdd").format(new Date()));
    projectDO.setAlexa(alexa);
    projectDO.setLocalRank(alexa);
    projectDO.setId(id);
    projectDao.updateById(projectDO);

    ProjectDO project = projectDao.getById(id);

    assertThat(project.getAlexa(), is(equalTo(alexa)));
    assertThat(project.getLocalRank(), is(equalTo(alexa)));
  }
}
