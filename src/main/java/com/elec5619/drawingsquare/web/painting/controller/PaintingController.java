package com.elec5619.drawingsquare.web.painting.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elec5619.drawingsquare.common.utils.ResultUtils;
import com.elec5619.drawingsquare.common.utils.ResultVo;
import com.elec5619.drawingsquare.web.painting.entity.Painting;
import com.elec5619.drawingsquare.web.painting.entity.PaintingParm;
import com.elec5619.drawingsquare.web.painting.service.PaintingService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 图画控制器
 */
@RestController
public class PaintingController {
    @Autowired
    private PaintingService paintingService;


    /**
     * 新增图画
     * @param painting
     * @return
     */
    @PostMapping
    public ResultVo add(@RequestBody Painting painting) {
        boolean save = paintingService.save(painting);
        if (save) {
            return ResultUtils.success("新增图画成功!");
        }
        return ResultUtils.error("新增图画失败!");
    }

    /**
     * 编辑图画
     */
    @PutMapping
    public ResultVo edit(@RequestBody Painting painting){
        boolean b = paintingService.updateById(painting);
        if(b){
            return ResultUtils.success("编辑问卷成功!");
        }
        return ResultUtils.error("编辑问卷失败!");
    }

    /**
     * 删除问卷
     * @return
     */
    @DeleteMapping("/{paintingId}")
    public ResultVo delete(@PathVariable("paintingId") int paintingId){
        boolean b = paintingService.removeById(paintingId);
        if(b){
            return ResultUtils.success("删除画图成功!");
        }
        return ResultUtils.error("删除画图失败!");
    }

    /**
     * 问卷列表
     */
    @GetMapping("/list")
    public ResultVo getList(PaintingParm paintingParm){
        //构造分页对象
        IPage<Painting> page = new Page<>();
        page.setSize(paintingParm.getPageSize());
        page.setCurrent(paintingParm.getCurrentPage());
        //构造查询条件
        QueryWrapper<Painting> query = new QueryWrapper<>();
        //模糊查询
        if(StringUtils.isNotEmpty(paintingParm.getPaintingName())){
            query.lambda().like(Painting::getPaintingName,paintingParm.getPaintingName());
        }
        //排序
        query.lambda().orderByAsc(Painting::getPaintingId);
        IPage<Painting> list = paintingService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

}
