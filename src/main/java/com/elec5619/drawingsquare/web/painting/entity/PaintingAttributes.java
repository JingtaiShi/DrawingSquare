package com.elec5619.drawingsquare.web.painting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("painting_attributes")
public class PaintingAttributes implements Serializable {

    @TableId(type = IdType.AUTO)
    // 属性Id
    private int attributesId;

    // 绘画Id
    @TableField("painting_id")
    private int paintingId;

    // 属性名称
    @TableField("attributes_name")
    private String attributesName;
}
