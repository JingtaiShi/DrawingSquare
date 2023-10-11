package com.elec5619.drawingsquare.web.painting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("purchased")
public class Purchased {

    @TableId(type = IdType.AUTO)
    private int purchasedId;

    @TableField("user_id")
    private int sysUserId;

    @TableField("painting_id")
    private int paintingId;
}
