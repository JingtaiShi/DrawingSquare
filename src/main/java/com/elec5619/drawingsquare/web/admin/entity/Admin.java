package com.elec5619.drawingsquare.web.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data // generate getter and setter
@TableName("admin") // Bind class to a database table
public class Admin implements Serializable {

    // Primary key, auto increase
    @TableId(type = IdType.AUTO)
    private int adminId;


    @TableField("admin_name")
    private String username;


    @TableField("admin_password")
    private String password;

}
