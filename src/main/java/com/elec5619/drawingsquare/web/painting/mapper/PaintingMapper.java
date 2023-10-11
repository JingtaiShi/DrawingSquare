package com.elec5619.drawingsquare.web.painting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elec5619.drawingsquare.web.painting.entity.Painting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaintingMapper extends BaseMapper<Painting> {

    // 根据点赞查询绘画
    List<Painting> listLikedPainting(@Param("userId") Long userId);

    // 根据购买查询绘画
    List<Painting> listPurchasePainting(@Param("userId") Long userId);
}
