package com.example.dao;


import com.example.config.JpaConfig;
import com.example.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.*;


//@ContextConfiguration(locations = {"/applicationContext.xml"})
@ContextConfiguration(classes = JpaConfig.class)
public class UserDaoTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void hasMatchUser() {
        Long count = userDao.getMatchCount("admin", "123456");
        assertTrue(count > 0);
    }

    @Test
    public void findUserByUserName() {
        User user = userDao.findUserByUserName("admin");
        assertNotNull(user);
        assertEquals(user.getUserName(), "admin");
    }

}
