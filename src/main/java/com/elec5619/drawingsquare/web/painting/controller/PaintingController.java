package com.elec5619.drawingsquare.web.painting.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elec5619.drawingsquare.common.utils.ResultUtils;
import com.elec5619.drawingsquare.common.utils.ResultVo;
import com.elec5619.drawingsquare.web.painting.entity.Liked;
import com.elec5619.drawingsquare.web.painting.entity.Painting;
import com.elec5619.drawingsquare.web.painting.entity.PaintingParm;
import com.elec5619.drawingsquare.web.painting.entity.Purchased;
import com.elec5619.drawingsquare.web.painting.service.LikedService;
import com.elec5619.drawingsquare.web.painting.service.PaintingService;
import com.elec5619.drawingsquare.web.painting.service.PurchasedService;
import com.elec5619.drawingsquare.web.user.entity.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 图画控制器
 */
@RestController
@RequestMapping("/api/painting")
public class PaintingController {
    @Autowired
    private PaintingService paintingService;

    @Autowired
    private PurchasedService purchasedService;

    @Autowired
    private LikedService likedService;

    /**
     * 根据用户id查询用户生成的画作
     * 参数: 用户id
     * 返回: 画作列表
     */
    @GetMapping("/getHistoryList")
    public ResultVo getHistoryList(Long userId){
        // 构造查询条件
        QueryWrapper<Painting> query = new QueryWrapper<>();

        query.lambda().eq(Painting::getUserId, userId);

        // 根据生成顺序排序
        query.lambda().orderByDesc(Painting::getPaintingId);

        List<Painting> paintings = paintingService.list(query);

        return ResultUtils.success("查询成功",paintings);
    }

    @GetMapping("/getLikedPaintingList")
    public ResultVo getLikedPaintingList(Long userId) {
        // 1. 构造查询条件，从 Purchased 表中查找匹配 userId 的 paintingId
        QueryWrapper<Liked> likedQueryWrapper = new QueryWrapper<>();
        likedQueryWrapper.lambda().eq(Liked::getSysUserId, userId);
        likedQueryWrapper.lambda().orderByDesc(Liked::getLikesId);
        List<Liked> likedList = likedService.list(likedQueryWrapper);

        // 2. 根据找到的 paintingId 查询 Painting 表中的信息
        List<Painting> paintingList = new ArrayList<>();
        for (Liked liked : likedList) {
            int paintingId = liked.getPaintingId();
            // 构造查询条件，从 Painting 表中查找匹配 paintingId 的信息
            QueryWrapper<Painting> paintingQuery = new QueryWrapper<>();
            paintingQuery.lambda().eq(Painting::getPaintingId, paintingId);
            Painting painting = paintingService.getOne(paintingQuery);
            if (painting != null) {
                paintingList.add(painting);
            }
        }
        return ResultUtils.success("查询成功",paintingList);
    }

    /**
     * 根据用户的id获取他购买的画作列表
     * 参数：用户id
     * 返回：画作列表
     *
     */
    @GetMapping("/getPruchesedPaintingList")
    public ResultVo getPruchesedPaintingList(Long userId) {
        // 1. 构造查询条件，从 Purchased 表中查找匹配 userId 的 paintingId
        QueryWrapper<Purchased> purchasedQuery = new QueryWrapper<>();
        purchasedQuery.lambda().eq(Purchased::getSysUserId, userId);
        purchasedQuery.lambda().orderByDesc(Purchased::getPurchasedId);
        List<Purchased> purchasedList = purchasedService.list(purchasedQuery);

        // 2. 根据找到的 paintingId 查询 Painting 表中的信息
        List<Painting> paintingList = new ArrayList<>();
        for (Purchased purchased : purchasedList) {
            int paintingId = purchased.getPaintingId();
            // 构造查询条件，从 Painting 表中查找匹配 paintingId 的信息
            QueryWrapper<Painting> paintingQuery = new QueryWrapper<>();
            paintingQuery.lambda().eq(Painting::getPaintingId, paintingId);
            Painting painting = paintingService.getOne(paintingQuery);
            if (painting != null) {
                paintingList.add(painting);
            }
        }
        return ResultUtils.success("查询成功",paintingList);
    }

    /**
     * 将购买的图片添加进购买库中addImageToLibrary
     */
    @PostMapping("/addImageToLibrary")
    public ResultVo addImageToLibrary(@RequestBody Purchased purchased) {
        // 在购买库中执行查询，检查是否已经存在相同的记录
        QueryWrapper<Purchased> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", purchased.getSysUserId()); // 假设存在一个"user_id"字段
        queryWrapper.eq("painting_id", purchased.getPaintingId()); // 假设存在一个"painting_id"字段
        Purchased existingPurchase = purchasedService.getOne(queryWrapper);

        if (existingPurchase != null) {
            return ResultUtils.error("已经购买过此图画!");
        }

        boolean save = purchasedService.save(purchased);
        if (save) {
            return ResultUtils.success("购买图画成功!");
        }
        return ResultUtils.error("购买图画失败!");
    }

    /**
     * 将喜爱的图片添加进喜爱库中
     */
    @PostMapping("/like")
    public ResultVo like(@RequestBody Liked liked) {
        // 在喜爱库中执行查询，检查是否已经存在相同的记录
        QueryWrapper<Liked> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", liked.getSysUserId()); // 假设存在一个"user_id"字段
        queryWrapper.eq("painting_id", liked.getPaintingId()); // 假设存在一个"painting_id"字段
        Liked existingLiked = likedService.getOne(queryWrapper);

        if (existingLiked != null) {
            return ResultUtils.error("已经收藏过此图画!");
        }

        Painting painting = paintingService.getById(liked.getPaintingId());
        painting.setLikes(painting.getLikes() + 1);
        boolean save = likedService.save(liked);
        boolean update = paintingService.saveOrUpdate(painting);
        if (save && update) {
            return ResultUtils.success("收藏图画成功!");
        }
        return ResultUtils.error("收藏图画失败!");
    }
    /**
     * 将喜爱的图片移除喜爱库
     */
    @PostMapping("/remove")
    public ResultVo removeLike(@RequestBody Liked liked) {
        Painting painting = paintingService.getById(liked.getPaintingId());
        painting.setLikes(painting.getLikes() - 1);
        // 创建 QueryWrapper 对象并设置查询条件
        QueryWrapper<Liked> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("painting_id", liked.getPaintingId()); // 设置删除条件

        boolean remove = likedService.remove(queryWrapper); // 使用 QueryWrapper 来删除数据
        boolean update = paintingService.saveOrUpdate(painting);
        if (remove && update) {
            return ResultUtils.success("取消收藏图画成功!");
        }
        return ResultUtils.error("取消收藏图画失败!");
    }


    /**
     * 获取图画信息
     */
    @GetMapping("/getImgInfo")
    public ResultVo getInfo(Long paintingId){
        Painting painting = paintingService.getById(paintingId);
        return ResultUtils.success("查询成功",painting);
    }
    /**
     * 新增图画
     *
     * @param painting
     * @return
     */
    @PostMapping("/addImgApi")
    public ResultVo add(@RequestBody Painting painting) {
        if(paintingService.getById(painting.getPaintingId()) != null) {
            boolean update = paintingService.updateById(painting);
            if (update) {
                return ResultUtils.success("编辑图画成功!");
            }
            return ResultUtils.error("编辑图画失败!");
        } else {
            boolean save = paintingService.save(painting);
            if (save) {
                return ResultUtils.success("新增图画成功!");
            }
            return ResultUtils.error("新增图画失败!");

        }


    }

    /**
     * 编辑图画
     */
    @PutMapping
    public ResultVo edit(@RequestBody Painting painting) {
        boolean b = paintingService.updateById(painting);
        if (b) {
            return ResultUtils.success("编辑图画成功!");
        }
        return ResultUtils.error("编辑图画失败!");
    }

    /**
     * 删除绘画
     *
     * @return
     */
    @DeleteMapping("/{paintingId}")
    public ResultVo delete(@PathVariable("paintingId") int paintingId) {
        boolean b = paintingService.removeById(paintingId);
        if (b) {
            return ResultUtils.success("删除画图成功!");
        }
        return ResultUtils.error("删除画图失败!");
    }

    /**
     * 根据tag搜索绘画列表
     * 参数：tag
     * 返回值：绘画列表
     */
    @GetMapping("/searchImage")
    public ResultVo searchImage(String tag) {
        // 构造查询条件
        QueryWrapper<Painting> query = new QueryWrapper<>();

        if(StringUtils.isNotEmpty(tag)) {
            query.lambda().like(Painting::getTag, tag);
        }

        // 根据点赞量排序
        query.lambda().orderByDesc(Painting::getLikes);

        List<Painting> paintings = paintingService.list(query);
        int count = 0;
        List<Painting> painting1 = new ArrayList<>();
        List<Painting> painting2 = new ArrayList<>();
        List<Painting> painting3 = new ArrayList<>();
        List<Painting> painting4 = new ArrayList<>();
        for(Painting painting : paintings) {
            if(count % 4 == 0) {
                painting1.add(painting);
            } else if(count % 4 == 1) {
                painting2.add(painting);
            } else if(count % 4 == 2) {
                painting3.add(painting);
            } else {
                painting4.add(painting);
            }
            count++;
        }
        List<List> list = new ArrayList<>();
        list.add(painting1);
        list.add(painting2);
        list.add(painting3);
        list.add(painting4);
        return ResultUtils.success("查询成功", list);
    }

    /**
     * 绘画列表
     */
    @GetMapping("/list")
    public ResultVo getList(PaintingParm paintingParm) {
        // 构造分页对象
        IPage<Painting> page = new Page<>();
        page.setSize(paintingParm.getPageSize());
        page.setCurrent(paintingParm.getCurrentPage());
        // 构造查询条件
        QueryWrapper<Painting> query = new QueryWrapper<>();
        // 模糊查询
        if (StringUtils.isNotEmpty(paintingParm.getTitle())) {
            query.lambda().like(Painting::getTitle, paintingParm.getTitle());
        }
        // 根据点赞量排序
        query.lambda().orderByDesc(Painting::getLikes);
        IPage<Painting> list = paintingService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }


    @GetMapping("/gallary")
    public ResultVo getList() {
        // 构造查询条件
        QueryWrapper<Painting> query = new QueryWrapper<>();
        query.eq("painting_status", 1);
        // 根据点赞量排序
        query.lambda().orderByDesc(Painting::getLikes);

        List<Painting> paintings = paintingService.list(query);
        int count = 0;
        List<Painting> painting1 = new ArrayList<>();
        List<Painting> painting2 = new ArrayList<>();
        List<Painting> painting3 = new ArrayList<>();
        List<Painting> painting4 = new ArrayList<>();
        for(Painting painting : paintings) {
            if(count % 4 == 0) {
                painting1.add(painting);
            } else if(count % 4 == 1) {
                painting2.add(painting);
            } else if(count % 4 == 2) {
                painting3.add(painting);
            } else {
                painting4.add(painting);
            }
            count++;
        }
        List<List> list = new ArrayList<>();
        list.add(painting1);
        list.add(painting2);
        list.add(painting3);
        list.add(painting4);
        return ResultUtils.success("查询成功", list);
    }



}
