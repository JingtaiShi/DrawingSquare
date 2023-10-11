package com.elec5619.drawingsquare.web.painting.entity;

import lombok.Data;

@Data
public class PaintingParm {
    //当前页
    private Long currentPage;
    //页容量 每页的条数
    private Long pageSize;
    //问卷标题
    private String title;
}
