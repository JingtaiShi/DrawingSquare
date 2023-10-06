package com.elec5619.drawingsquare.web.aiart.eitity;

import lombok.Data;

import java.util.List;

@Data
public class AIResponseData {
    private String style;
    private Integer taskId;
    private String imgUrls;
    private String text;
    private Integer status;
    private String createTime;
    private String img;
    private String waiting;
}