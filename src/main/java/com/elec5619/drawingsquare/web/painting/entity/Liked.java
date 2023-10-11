package com.elec5619.drawingsquare.web.painting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("likes")
public class Liked {

    @TableId(type = IdType.AUTO)
    private int likesId;

    @TableField("user_id")
    private int sysUserId;

    @TableField("painting_id")
    private int paintingId;

    @TableField(exist = false)
    private int likes;
}
