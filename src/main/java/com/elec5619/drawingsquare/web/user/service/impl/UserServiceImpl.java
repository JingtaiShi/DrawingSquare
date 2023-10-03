package com.elec5619.drawingsquare.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.elec5619.drawingsquare.web.user.entity.User;
import com.elec5619.drawingsquare.web.user.mapper.UserMapper;
import com.elec5619.drawingsquare.web.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
