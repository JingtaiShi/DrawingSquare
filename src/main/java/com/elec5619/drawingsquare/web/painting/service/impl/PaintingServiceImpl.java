package com.elec5619.drawingsquare.web.painting.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.drawingsquare.web.painting.entity.Painting;
import com.elec5619.drawingsquare.web.painting.mapper.PaintingMapper;
import com.elec5619.drawingsquare.web.painting.service.PaintingService;
import org.springframework.stereotype.Service;



@Service
public class PaintingServiceImpl extends ServiceImpl<PaintingMapper, Painting> implements PaintingService {

}
