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
 * Painting controller
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
     * Query user-generated paintings based on user id
     * params: userId
     * return: paintingList
     */
    @GetMapping("/getHistoryList")
    public ResultVo getHistoryList(Long userId){
        // Construct query condition
        QueryWrapper<Painting> query = new QueryWrapper<>();

        query.lambda().eq(Painting::getUserId, userId);

        // Sort according to build order
        query.lambda().orderByDesc(Painting::getPaintingId);

        List<Painting> paintings = paintingService.list(query);

        return ResultUtils.success("query successful",paintings);
    }

    @GetMapping("/getLikedPaintingList")
    public ResultVo getLikedPaintingList(Long userId) {
        // Construct a query condition to find the paintingId matching userId from the Liked table
        QueryWrapper<Liked> likedQueryWrapper = new QueryWrapper<>();
        likedQueryWrapper.lambda().eq(Liked::getSysUserId, userId);
        likedQueryWrapper.lambda().orderByDesc(Liked::getLikesId);
        List<Liked> likedList = likedService.list(likedQueryWrapper);

        // Query the information in the Painting table according to the paintingId found
        List<Painting> paintingList = new ArrayList<>();
        for (Liked liked : likedList) {
            int paintingId = liked.getPaintingId();
            // Construct the query criteria to find information matching the paintingId from the Painting table
            QueryWrapper<Painting> paintingQuery = new QueryWrapper<>();
            paintingQuery.lambda().eq(Painting::getPaintingId, paintingId);
            Painting painting = paintingService.getOne(paintingQuery);
            if (painting != null) {
                paintingList.add(painting);
            }
        }
        return ResultUtils.success("query successful",paintingList);
    }

    /**
     * Query user-purchased paintings based on user id
     * params: userId
     * return: paintingList
     *
     */
    @GetMapping("/getPurchasedPaintingList")
    public ResultVo getPurchasedPaintingList(Long userId) {
        // Construct a query condition to find the paintingId matching userId from the Purchased table
        QueryWrapper<Purchased> purchasedQuery = new QueryWrapper<>();
        purchasedQuery.lambda().eq(Purchased::getSysUserId, userId);
        purchasedQuery.lambda().orderByDesc(Purchased::getPurchasedId);
        List<Purchased> purchasedList = purchasedService.list(purchasedQuery);

        // Query the information in the Painting table according to the paintingId found
        List<Painting> paintingList = new ArrayList<>();
        for (Purchased purchased : purchasedList) {
            int paintingId = purchased.getPaintingId();
            // Construct the query criteria to find information matching the paintingId from the Painting table
            QueryWrapper<Painting> paintingQuery = new QueryWrapper<>();
            paintingQuery.lambda().eq(Painting::getPaintingId, paintingId);
            Painting painting = paintingService.getOne(paintingQuery);
            if (painting != null) {
                paintingList.add(painting);
            }
        }
        return ResultUtils.success("query successful",paintingList);
    }

    /**
     * add purchased image to purchased library
     */
    @PostMapping("/addImageToLibrary")
    public ResultVo addImageToLibrary(@RequestBody Purchased purchased) {
        // Perform a query in the purchase table to check if the same record already exists
        QueryWrapper<Purchased> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", purchased.getSysUserId());
        queryWrapper.eq("painting_id", purchased.getPaintingId());
        Purchased existingPurchase = purchasedService.getOne(queryWrapper);

        if (existingPurchase != null) {
            return ResultUtils.error("This drawing has been purchased!");
        }

        boolean save = purchasedService.save(purchased);
        if (save) {
            return ResultUtils.success("Purchase drawing success!");
        }
        return ResultUtils.error("Purchase drawing error!");
    }

    /**
     * add liked image to liked library
     */
    @PostMapping("/like")
    public ResultVo like(@RequestBody Liked liked) {
        // Perform a query in the liked table to check if the same record already exists
        QueryWrapper<Liked> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", liked.getSysUserId());
        queryWrapper.eq("painting_id", liked.getPaintingId());
        Liked existingLiked = likedService.getOne(queryWrapper);

        if (existingLiked != null) {
            return ResultUtils.error("Have collected this picture!");
        }

        Painting painting = paintingService.getById(liked.getPaintingId());
        painting.setLikes(painting.getLikes() + 1);
        boolean save = likedService.save(liked);
        boolean update = paintingService.saveOrUpdate(painting);
        if (save && update) {
            return ResultUtils.success("Picture collection success!");
        }
        return ResultUtils.error("Picture collection error!");
    }
    /**
     * Remove a liked image from your liked table
     */
    @PostMapping("/remove")
    public ResultVo removeLike(@RequestBody Liked liked) {
        Painting painting = paintingService.getById(liked.getPaintingId());
        painting.setLikes(painting.getLikes() - 1);
        // Create the QueryWrapper object and set the query criteria
        QueryWrapper<Liked> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("painting_id", liked.getPaintingId()); // Set deletion conditions

        boolean remove = likedService.remove(queryWrapper); // Use QueryWrapper to delete the data
        boolean update = paintingService.saveOrUpdate(painting);
        if (remove && update) {
            return ResultUtils.success("Remove liked drawing success!");
        }
        return ResultUtils.error("Remove liked drawing error!");
    }


    /**
     * get drawing information
     */
    @GetMapping("/getImgInfo")
    public ResultVo getInfo(Long paintingId){
        Painting painting = paintingService.getById(paintingId);
        return ResultUtils.success("query successful",painting);
    }
    /**
     * add drawing
     *
     * @param painting
     * @return
     */
    @PostMapping("/addImgApi")
    public ResultVo add(@RequestBody Painting painting) {
        if(paintingService.getById(painting.getPaintingId()) != null) {
            boolean update = paintingService.updateById(painting);
            if (update) {
                return ResultUtils.success("edit drawing successful");
            }
            return ResultUtils.error("edit drawing error!");
        } else {
            boolean save = paintingService.save(painting);
            if (save) {
                return ResultUtils.success("add drawing successful!");
            }
            return ResultUtils.error("add drawing error");

        }


    }

    /**
     * edit drawings
     */
    @PutMapping
    public ResultVo edit(@RequestBody Painting painting) {
        boolean b = paintingService.updateById(painting);
        if (b) {
            return ResultUtils.success("edit drawings successful!");
        }
        return ResultUtils.error("edit drawings error!");
    }

    /**
     * delete drawings
     *
     * @return
     */
    @DeleteMapping("/{paintingId}")
    public ResultVo delete(@PathVariable("paintingId") int paintingId) {
        boolean b = paintingService.removeById(paintingId);
        if (b) {
            return ResultUtils.success("delete drawing successful!");
        }
        return ResultUtils.error("delete drawing error!");
    }

    /**
     * Search the list of paintings according to tag
     * params：tag
     * return：painting list
     */
    @GetMapping("/searchImage")
    public ResultVo searchImage(String tag) {
        // Construct query condition
        QueryWrapper<Painting> query = new QueryWrapper<>();

        if(StringUtils.isNotEmpty(tag)) {
            query.lambda().like(Painting::getTag, tag);
        }

        // Sort by likes
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
        return ResultUtils.success("query successful", list);
    }

    /**
     * get painting list
     */
    @GetMapping("/list")
    public ResultVo getList(PaintingParm paintingParm) {
        // Construct paging objects
        IPage<Painting> page = new Page<>();
        page.setSize(paintingParm.getPageSize());
        page.setCurrent(paintingParm.getCurrentPage());
        // Construct query condition
        QueryWrapper<Painting> query = new QueryWrapper<>();
        // fuzzy query
        if (StringUtils.isNotEmpty(paintingParm.getTitle())) {
            query.lambda().like(Painting::getTitle, paintingParm.getTitle());
        }
        // Sort by likes
        query.lambda().orderByDesc(Painting::getLikes);
        IPage<Painting> list = paintingService.page(page, query);
        return ResultUtils.success("query successful", list);
    }


    @GetMapping("/gallary")
    public ResultVo getList() {
        // Construct paging objects
        QueryWrapper<Painting> query = new QueryWrapper<>();
        query.eq("painting_status", 1);
        // Sort by likes
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
        return ResultUtils.success("query successful", list);
    }



}
