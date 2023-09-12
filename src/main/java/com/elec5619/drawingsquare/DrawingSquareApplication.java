package com.elec5619.drawingsquare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @MapperScan({"com.elec5619.drawingsquare.web.user.mapper","com.elec5619.drawingsquare.web.painting.mapper"})
public class DrawingSquareApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrawingSquareApplication.class, args);
    }

}
