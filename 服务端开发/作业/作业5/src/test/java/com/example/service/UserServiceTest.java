package com.example.service;


import com.example.config.JpaConfig;
import com.example.dao.LoginLogDao;
import com.example.dao.UserDao;
import com.example.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

//@ContextConfiguration(locations = {"/applicationContext.xml"})
@ContextConfiguration(classes = JpaConfig.class)
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Mock
    private LoginLogDao loginLogDao;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void hasMatchUser() {
        Mockito.when(userDao.getMatchCount("admin", "123456")).thenReturn(1L);
        Mockito.when(userDao.getMatchCount("admin", "1111")).thenReturn(0L);

        boolean b1 = userService.hasMatchUser("admin", "123456");
        boolean b2 = userService.hasMatchUser("admin", "1111");
        assertTrue(b1);
        assertFalse(b2);
    }


    @Test
    public void findUserByUserName() {
        User u = new User();
        u.setUserId(1);
        u.setUserName("admin");
        u.setLastIp("192.168.12.7");
        u.setLastVisit(new Date());
        Mockito.when(userDao.findUserByUserName("admin")).thenReturn(u);

        User user = userService.findUserByUserName("admin");
        assertEquals(user.getUserName(), "admin");
    }

    @Test
    public void loginSuccess() {
        User u = new User();
        u.setUserId(1);
        u.setUserName("admin");
        u.setLastIp("192.168.12.7");
        u.setLastVisit(new Date());
        Mockito.when(userDao.findUserByUserName("admin")).thenReturn(u);

        User user = userService.findUserByUserName("admin");
        user.setUserId(1);
        user.setUserName("admin");
        user.setLastIp("192.168.12.7");
        user.setLastVisit(new Date());
        userService.saveLog(user);
    }
}

