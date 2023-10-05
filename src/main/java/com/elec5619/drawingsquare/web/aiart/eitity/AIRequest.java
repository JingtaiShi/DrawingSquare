package com.elec5619.drawingsquare.web.aiart.eitity;

import lombok.Data;

@Data
public class AIRequest {
    private String text;
    private String style;
    private String resolution;
    private Integer num;
}

