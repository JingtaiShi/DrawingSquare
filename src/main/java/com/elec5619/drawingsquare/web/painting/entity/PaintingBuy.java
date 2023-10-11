package com.elec5619.drawingsquare.web.painting.entity;

import lombok.Data;

@Data
public class PaintingBuy {

    private String sysUserId;

    private double sysUserMoney;

    private String userId;

    private double userMoney;

    private double price;
}
