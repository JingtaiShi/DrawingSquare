package com.elec5619.drawingsquare.web.user.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserParm implements Serializable {

    private String username;

    private Long currentPage;

    private Long pageSize;


}
