package com.elec5619.drawingsquare.web.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elec5619.drawingsquare.common.utils.ResultUtils;
import com.elec5619.drawingsquare.common.utils.ResultVo;

import com.elec5619.drawingsquare.web.painting.entity.PaintingBuy;
import com.elec5619.drawingsquare.web.painting.service.PaintingService;
import com.elec5619.drawingsquare.web.user.entity.User;
import com.elec5619.drawingsquare.web.user.entity.UserParm;
import com.elec5619.drawingsquare.web.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * user controller
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    public UserService userService;

    @Autowired
    public PaintingService paintingService;
    /**
     * user login
     */
    @PostMapping("/login")
    public ResultVo login(@RequestBody User user){
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUsername,user.getUsername())
                .eq(User::getPassword,user.getPassword());
        User one = userService.getOne(query);
        if(one == null){
            return ResultUtils.error("用户名或密码错误");
        }
        return ResultUtils.success("login successful",one);
    }
    /**
     * get user information
     */
    @GetMapping("/getUserInfo")
    public ResultVo getUserInfo(Long userId){
        User user = userService.getById(userId);
        return ResultUtils.success("query successful",user);
    }
    /**
     * add user
     */
    @PostMapping
    public ResultVo add(@RequestBody User user){
        boolean save = userService.save(user);
        if(save){
            return ResultUtils.success("add user successful!");
        }
        return ResultUtils.error("add user error!");
    }
    /**
     * edit
     */
    @PutMapping
    public ResultVo edit(@RequestBody User user){
        boolean b = userService.updateById(user);
        if(b){
            return ResultUtils.success("edit user successful!");
        }
        return ResultUtils.error("edit user error!!");
    }

    /**
     * delete
     */
    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId){
        boolean b = userService.removeById(userId);
        if(b){
            return ResultUtils.success("delete user successful!");
        }
        return ResultUtils.error("delete user error!!");
    }

    /**
     * get user list
     */
    @GetMapping("/list")
    public ResultVo getList(UserParm userParm){

        System.out.println(userParm.getUsername());
        // Construct paging objects
        IPage<User> page = new Page<>();
        page.setCurrent(userParm.getCurrentPage());
        page.setSize(userParm.getPageSize());
        // Construct query condition
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().like(User::getUsername, userParm.getUsername());
        IPage<User> list = userService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * Painting trade method
     * Param: Buyer id, Buyer money, painting owner id, painting owner money, painting price
     * return：success
     */
    @PostMapping("/buy")
    public ResultVo buy(@RequestBody PaintingBuy paintingBuy) {
        // 1. query user information
        User sysUser = userService.getById(paintingBuy.getSysUserId());
        User user = userService.getById(paintingBuy.getUserId());

        // 2. update user information
        if(sysUser.getMoney() >= paintingBuy.getPrice()) {
            sysUser.setMoney((int) (sysUser.getMoney() - paintingBuy.getPrice()));
            user.setMoney((int) (user.getMoney() + paintingBuy.getPrice()));
            // 3. Save the updated user information to the database
            boolean a = userService.saveOrUpdate(sysUser);
            boolean b = userService.saveOrUpdate(user);
            if(a && b) {
                return ResultUtils.success("Purchase successful");
            } else {
                return ResultUtils.error("Purchase failed");
            }
        } else {
            return ResultUtils.error("Purchase failed");
        }

    }
}
