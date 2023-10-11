package com.elec5619.drawingsquare.web.painting.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.drawingsquare.web.painting.entity.Liked;
import com.elec5619.drawingsquare.web.painting.entity.Purchased;
import com.elec5619.drawingsquare.web.painting.mapper.LikedMapper;
import com.elec5619.drawingsquare.web.painting.mapper.PurchasedMapper;
import com.elec5619.drawingsquare.web.painting.service.LikedService;
import com.elec5619.drawingsquare.web.painting.service.PurchasedService;
import org.springframework.stereotype.Service;


@Service
public class LikedServiceImpl extends ServiceImpl<LikedMapper, Liked> implements LikedService {

}
