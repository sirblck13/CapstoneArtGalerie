package com.techelevator.tests;

import com.techelevator.dao.UserSqlDAO;
import com.techelevator.model.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

import javax.sql.DataSource;

public class UserSqlDaoIntegrationTest extends DAOIntegrationTest {

    private UserSqlDAO userSqlDAO;
    
    @BeforeClass
    public static void setupData() {
    	setupDataSource();
    	
    }
    
    @AfterClass
    public static void closeData() throws SQLException {
    	closeDataSource();
    }

    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userSqlDAO = new UserSqlDAO(jdbcTemplate);
    }
    
    @After
    public void roll() throws SQLException {
        rollback();
    }

    @Test
    public void createNewUser() {
        boolean userCreated = userSqlDAO.create("TEST_USER","test_password","user", "fakeemail@name.com");
        Assert.assertTrue(userCreated);
        User user = userSqlDAO.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

}
