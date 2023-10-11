package com.elec5619.drawingsquare.web.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data // 自动生成get和set方法
@TableName("admin") // 指定Admin对应表名为admin
public class Admin implements Serializable {

    // 指明主键，自动递增
    @TableId(type = IdType.AUTO)
    private int adminId;

    // 登陆账户
    @TableField("admin_name")
    private String username;

    // 登陆密码
    @TableField("admin_password")
    private String password;

}
