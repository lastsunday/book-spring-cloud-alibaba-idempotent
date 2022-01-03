package com.github.lastsunday.cloud.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.lastsunday.cloud.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<UserEntity> {

    List<UserEntity> getUserAndAddr(@Param("username") String username);
}
