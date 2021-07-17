package com.example.web;

import com.example.domain.User;
import com.example.service.UserService;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class LoginControllerTest {

    // 测试登陆失败的情况
    @Test
    public void loginFail() throws Exception {
        UserService userService = mock(UserService.class);
        when(userService.hasMatchUser("test", "123456")).thenReturn(false);

        LoginController controller = new LoginController(userService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/admin/loginCheck.html")
                .param("userName", "test")
                .param("password", "123456"))
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));

        verify(userService).hasMatchUser("test", "123456");
    }

    //测试登陆成功的情况
    @Test
    public void loginSuccess() throws Exception {
        UserService userService = mock(UserService.class);

        when(userService.hasMatchUser("admin", "123456")).thenReturn(true);

        User user = new User(1, "admin");
        when(userService.findUserByUserName("admin")).thenReturn(user);
        when(userService.saveLog(anyObject())).thenReturn(true);

        LoginController controller = new LoginController(userService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/admin/loginCheck.html")
                .param("userName", "admin")
                .param("password", "123456"))
                .andExpect(view().name("main"));

        verify(userService).hasMatchUser("admin", "123456");
        verify(userService).findUserByUserName("admin");
        verify(userService).saveLog(anyObject());
    }
}
