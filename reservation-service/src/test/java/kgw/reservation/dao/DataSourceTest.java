package kgw.reservation.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import naverest.reservation.config.RootApplicationContextConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
public class DataSourceTest {
	@Autowired
	DataSource dataSource;
	
	@Test
    public void connectionTest() throws Exception{
        Connection connection = dataSource.getConnection();
        Assert.assertNotNull(connection);
    }
}
