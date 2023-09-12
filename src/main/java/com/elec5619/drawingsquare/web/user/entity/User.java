package com.elec5619.drawingsquare.web.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data // 自动生成get和set方法
@TableName("user") // 指定User对应表名为user
public class User implements Serializable {

    // 指明主键，自动递增
    @TableId(type = IdType.AUTO)
    private int userId;

    // 登陆账户
    private String username;

    // 登陆密码
    private String password;

    // 邮箱
    private String email;

    // 电话
    private String phone;

    // 钱包
    private int money;



}
