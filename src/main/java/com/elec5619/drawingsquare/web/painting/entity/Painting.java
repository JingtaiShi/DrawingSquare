package com.elec5619.drawingsquare.web.painting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("painting")
public class Painting implements Serializable {

    @TableId(type = IdType.AUTO)
    // 画图Id
    private int paintingId;

    // 用户Id
    @TableField("user_id")
    private int userId;

    // 绘画名称
    @TableField("painting_name")
    private String title;

    // 绘画地址
    @TableField("painting_url")
    private String url;

    // 绘画状态
    @TableField("painting_status")
    private String status;

    // 绘画价格
    @TableField("painting_price")
    private double price;

    // 点赞数
    @TableField("painting_likes")
    private int likes;

    // 绘画属性
    @TableField("painting_tag")
    private String tag;

    // 绘画描述
    @TableField("painting_description")
    private String description;
}
