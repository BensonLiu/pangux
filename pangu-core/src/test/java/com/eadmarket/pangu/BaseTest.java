package com.eadmarket.pangu;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基类
 * 
 * @author liuyongpo@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean/applicationContext.xml")
public class BaseTest extends AbstractJUnit4SpringContextTests {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@Resource protected JdbcTemplate adJdbcTemplate;
}
