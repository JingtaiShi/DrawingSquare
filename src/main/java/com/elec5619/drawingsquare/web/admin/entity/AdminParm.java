package com.elec5619.drawingsquare.web.admin.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class AdminParm implements Serializable {
    //当前第几页
    private Long currentPage;
    //页容量
    private Long pageSize;
    // 用户名称
    private String adminName;

}