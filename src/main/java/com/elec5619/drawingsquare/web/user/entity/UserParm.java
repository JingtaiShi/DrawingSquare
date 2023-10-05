package com.elec5619.drawingsquare.web.user.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserParm implements Serializable {
    // 用户名称
    private String username;
    //当前第几页
    private Long currentPage;
    //页容量
    private Long pageSize;


}