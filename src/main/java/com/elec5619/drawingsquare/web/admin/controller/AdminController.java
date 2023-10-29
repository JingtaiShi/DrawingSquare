package com.elec5619.drawingsquare.web.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elec5619.drawingsquare.common.utils.ResultUtils;
import com.elec5619.drawingsquare.common.utils.ResultVo;

import com.elec5619.drawingsquare.web.admin.entity.Admin;
import com.elec5619.drawingsquare.web.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Admin Controller
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    public AdminService adminService;

    /**
     * Admin login
     */
    @PostMapping("/AdminLogin")
    public ResultVo AdminLogin(@RequestBody Admin admin){
        QueryWrapper<Admin> query = new QueryWrapper<>();
        query.lambda().eq(Admin::getUsername, admin.getUsername())
                .eq(Admin::getPassword, admin.getPassword());
        Admin one = adminService.getOne(query);
        if(one == null){
            return ResultUtils.error("error account or password");
        }
        return ResultUtils.success("success",one.getAdminId());
    }
}
