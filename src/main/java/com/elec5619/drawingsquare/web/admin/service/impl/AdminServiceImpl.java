package com.elec5619.drawingsquare.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.drawingsquare.web.admin.entity.Admin;
import com.elec5619.drawingsquare.web.admin.mapper.AdminMapper;
import com.elec5619.drawingsquare.web.admin.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
