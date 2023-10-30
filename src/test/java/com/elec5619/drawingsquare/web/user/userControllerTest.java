package com.elec5619.drawingsquare.web.user;

import com.elec5619.drawingsquare.common.utils.ResultVo;
import com.elec5619.drawingsquare.web.painting.entity.PaintingBuy;
import com.elec5619.drawingsquare.web.painting.service.PaintingService;
import com.elec5619.drawingsquare.web.user.controller.UserController;
import com.elec5619.drawingsquare.web.user.entity.User;
import com.elec5619.drawingsquare.web.user.entity.UserParm;
import com.elec5619.drawingsquare.web.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class userControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private PaintingService paintingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin() {
        // Mock user and query result
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("password");
        when(userService.getOne(any())).thenReturn(mockUser);

        // Test the login method
        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("password");

        ResultVo result = userController.login(loginUser);

        assertEquals("login successful", result.getMsg());
        assertEquals(200, result.getCode());
        assertEquals(mockUser, result.getData());
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Mock user and query result
        when(userService.getOne(any())).thenReturn(null);

        // Test the login method with invalid credentials
        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("invalidPassword");

        ResultVo result = userController.login(loginUser);

        assertEquals("username or password error", result.getMsg());
        assertEquals(500, result.getCode());
        assertNull(result.getData());
    }


    @Test
    public void testGetUserInfo() {
        // Mock user and query result
        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setUsername("testuser");
        when(userService.getById(1L)).thenReturn(mockUser);

        // Test the getUserInfo method
        ResultVo result = userController.getUserInfo(1L);

        assertEquals("query successful", result.getMsg());
        assertEquals(200, result.getCode());
        assertEquals(mockUser, result.getData());
    }

    @Test
    public void testAddUser() {
        // Mock the save method
        doNothing().when(userService).save(any(User.class));

        // Test the add method
        User newUser = new User();
        ResultVo result = userController.add(newUser);

        assertEquals("add user successful!", result.getMsg());
        assertEquals(200, result.getCode());
    }

    @Test
    public void testEditUser() {
        // Mock the updateById method to return true when the update is successful
        when(userService.updateById(any(User.class))).thenReturn(true);

        // Test the edit method
        User updatedUser = new User();
        ResultVo result = userController.edit(updatedUser);

        assertEquals("edit user successful!", result.getMsg());
        assertEquals(200, result.getCode());
    }

    @Test
    public void testDeleteUser() {
        // Mock the removeById method
        when(userService.removeById(1L)).thenReturn(true);

        // Test the delete method
        ResultVo result = userController.delete(1L);

        assertEquals("delete user successful!", result.getMsg());
        assertEquals(200, result.getCode());
    }

    @Test
    public void testBuyPainting() {
        // Mock user and painting buy data
        User sysUser = new User();
        sysUser.setUserId(1);
        sysUser.setMoney(100);
        User user = new User();
        user.setUserId(2);
        user.setMoney(50);

        PaintingBuy paintingBuy = new PaintingBuy();
        paintingBuy.setSysUserId(String.valueOf(1));
        paintingBuy.setUserId(String.valueOf(2));
        paintingBuy.setPrice(25);

        // Mock the getById method for both users
        when(userService.getById(1L)).thenReturn(sysUser);
        when(userService.getById(2L)).thenReturn(user);

        // Mock the saveOrUpdate method for both users
        when(userService.saveOrUpdate(sysUser)).thenReturn(true);
        when(userService.saveOrUpdate(user)).thenReturn(true);

        // Test the buy method
        ResultVo result = userController.buy(paintingBuy);

        assertEquals("Purchase successful", result.getMsg());
        assertEquals(200, result.getCode());
    }
}
