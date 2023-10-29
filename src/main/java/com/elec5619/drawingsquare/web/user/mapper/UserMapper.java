package com.elec5619.drawingsquare.web.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elec5619.drawingsquare.web.user.entity.User;
/**
 *  data access layer
 *  extend BaseMapper of mybatis plus，
 *  we can use CRUD methods it provides.
 *
 */
public interface UserMapper extends BaseMapper<User> {
}
