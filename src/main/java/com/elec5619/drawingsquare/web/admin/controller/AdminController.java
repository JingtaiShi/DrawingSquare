package com.elec5619.drawingsquare.web.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elec5619.drawingsquare.common.utils.ResultUtils;
import com.elec5619.drawingsquare.common.utils.ResultVo;

import com.elec5619.drawingsquare.web.admin.entity.Admin;
import com.elec5619.drawingsquare.web.admin.entity.AdminParm;
import com.elec5619.drawingsquare.web.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理模块控制器
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    public AdminService adminService;

    /**
     * 用户登录
     */
    @PostMapping("/AdminLogin")
    public ResultVo AdminLogin(@RequestBody Admin admin){
        QueryWrapper<Admin> query = new QueryWrapper<>();
        query.lambda().eq(Admin::getUsername, admin.getUsername())
                .eq(Admin::getPassword, admin.getPassword());
        Admin one = adminService.getOne(query);
        if(one == null){
            return ResultUtils.error("用户名或密码错误");
        }
        return ResultUtils.success("登录成功",one.getAdminId());
    }
    /**
     * 获取用户信息
     */
    @GetMapping("/getInfo")
    public ResultVo getInfo(Long adminId){
        Admin admin = adminService.getById(adminId);
        return ResultUtils.success("查询成功",admin.getUsername());
    }
    /**
     * 新增
     */
    @PostMapping
    public ResultVo add(@RequestBody Admin admin){
        boolean save = adminService.save(admin);
        if(save){
            return ResultUtils.success("新增用户成功!");
        }
        return ResultUtils.error("新增用户失败!");
    }
    /**
     * 编辑
     */
    @PutMapping
    public ResultVo edit(@RequestBody Admin admin){
        boolean b = adminService.updateById(admin);
        if(b){
            return ResultUtils.success("编辑用户成功!");
        }
        return ResultUtils.error("编辑用户失败!");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{adminId}")
    public ResultVo delete(@PathVariable("adminId") Long adminId){
        boolean b = adminService.removeById(adminId);
        if(b){
            return ResultUtils.success("删除用户成功!");
        }
        return ResultUtils.error("删除用户失败!");
    }

    /**
     * 列表查询
     */
    @GetMapping("/list")
    public ResultVo getList(AdminParm adminParm){
        //构造分页对象
        IPage<Admin> page = new Page<>();
        page.setCurrent(adminParm.getCurrentPage());
        page.setSize(adminParm.getPageSize());
        IPage<Admin> list = adminService.page(page);
        return ResultUtils.success("查询成功",list);
    }
}
