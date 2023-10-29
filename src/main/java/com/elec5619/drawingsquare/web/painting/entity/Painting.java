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

    private int paintingId;

    @TableField("user_id")
    private int userId;

    @TableField("painting_name")
    private String title;

    @TableField("painting_url")
    private String url;

    @TableField("painting_status")
    private String status;

    @TableField("painting_price")
    private double price;

    @TableField("painting_likes")
    private int likes;

    @TableField("painting_tag")
    private String tag;

    @TableField("painting_description")
    private String description;
}
