package com.elec5619.drawingsquare.web.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elec5619.drawingsquare.web.user.entity.User;
/**
 * 用户的数据访问层
 *  继承 mybatis plus 的 BaseMapper，
 *  可以使用他提供的通用的增删改查的方法
 *
 */
public interface UserMapper extends BaseMapper<User> {
}
