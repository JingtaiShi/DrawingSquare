package com.elec5619.drawingsquare.web.painting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    private int userId;

    // 绘画名称
    private String paintingName;

    // 绘画地址
    private String paintingUrl;

    // 绘画状态
    private String paintingStatus;

    // 绘画价格
    private double paintingPrice;

    // 点赞数
    private int paintingLikes;
}
