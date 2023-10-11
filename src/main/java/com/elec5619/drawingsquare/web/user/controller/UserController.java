package com.elec5619.drawingsquare.web.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elec5619.drawingsquare.common.utils.ResultUtils;
import com.elec5619.drawingsquare.common.utils.ResultVo;

import com.elec5619.drawingsquare.web.painting.entity.Liked;
import com.elec5619.drawingsquare.web.painting.entity.Painting;
import com.elec5619.drawingsquare.web.painting.entity.PaintingBuy;
import com.elec5619.drawingsquare.web.painting.service.PaintingService;
import com.elec5619.drawingsquare.web.user.entity.User;
import com.elec5619.drawingsquare.web.user.entity.UserParm;
import com.elec5619.drawingsquare.web.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理模块控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    public UserService userService;

    @Autowired
    public PaintingService paintingService;
    /**
     * 用户登录
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
        return ResultUtils.success("登录成功",one);
    }
    /**
     * 获取用户信息
     */
    @GetMapping("/getUserInfo")
    public ResultVo getUserInfo(Long userId){
        User user = userService.getById(userId);
        return ResultUtils.success("查询成功",user);
    }
    /**
     * 新增
     */
    @PostMapping
    public ResultVo add(@RequestBody User user){
        boolean save = userService.save(user);
        if(save){
            return ResultUtils.success("新增用户成功!");
        }
        return ResultUtils.error("新增用户失败!");
    }
    /**
     * 编辑
     */
    @PutMapping
    public ResultVo edit(@RequestBody User user){
        boolean b = userService.updateById(user);
        if(b){
            return ResultUtils.success("编辑用户成功!");
        }
        return ResultUtils.error("编辑用户失败!");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId){
        boolean b = userService.removeById(userId);
        if(b){
            return ResultUtils.success("删除用户成功!");
        }
        return ResultUtils.error("删除用户失败!");
    }

    /**
     * 列表查询
     */
    @GetMapping("/list")
    public ResultVo getList(UserParm userParm){

        System.out.println(userParm.getUsername());
        // 构造分页对象
        IPage<User> page = new Page<>();
        page.setCurrent(userParm.getCurrentPage());
        page.setSize(userParm.getPageSize());
        // 构造查询条件
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().like(User::getUsername, userParm.getUsername());
        IPage<User> list = userService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 画作交易方法
     * 参数：购买者id，购买者money，画作拥有者id，画作拥有者money，画作价格
     * 返回：success
     */
    @PostMapping("/buy")
    public ResultVo buy(@RequestBody PaintingBuy paintingBuy) {
        // 1. 查询用户信息
        User sysUser = userService.getById(paintingBuy.getSysUserId());
        User user = userService.getById(paintingBuy.getUserId());

        // 3. 更新金额
        if(sysUser.getMoney() >= paintingBuy.getPrice()) {
            sysUser.setMoney((int) (sysUser.getMoney() - paintingBuy.getPrice()));
            user.setMoney((int) (user.getMoney() + paintingBuy.getPrice()));
            // 4. 保存更新后的用户信息到数据库
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
