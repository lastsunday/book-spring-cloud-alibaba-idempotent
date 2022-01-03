package com.github.lastsunday.cloud.service;

import com.github.lastsunday.cloud.entity.UserEntity;
import com.github.lastsunday.cloud.mapper.UserMapper;
import com.github.lastsunday.cloud.service.dto.UserDTO;
import com.github.lastsunday.cloud.service.request.UserServiceRequest;
import com.github.lastsunday.cloud.service.response.DefaultResult;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@DubboService(version = "1.0.0", group = "idempotent-user-server")
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TenantService tenantService;

    @Override
    public DefaultResult<List<UserDTO>> getUserInfo(UserServiceRequest userServiceRequest) {
        DefaultResult<List<UserDTO>> result = new DefaultResult<List<UserDTO>>();
        if (!StringUtils.isEmpty(userServiceRequest.getUuid())) {
            long uuid = Long.valueOf(userServiceRequest.getUuid());
            if (null != redisTemplate.opsForValue().get(uuid)) {
                result.setData(new ArrayList<>());
                result.setMessage("uuid:" + uuid + "already access multiple times;");
                return result;
            } else {
                redisTemplate.opsForValue().set(uuid, true, 2, TimeUnit.SECONDS);
            }
        }
        tenantService.loginTenant(Long.parseLong(userServiceRequest.getTenantId()));
        String username = userServiceRequest.getUsername();
        List<UserEntity> queryResult = userMapper.getUserAndAddr(username);
        List<UserDTO> dataResult = new ArrayList<>();
        if (!CollectionUtils.isEmpty(queryResult)) {
            for (UserEntity userEntity : queryResult) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(userEntity.getId());
                userDTO.setAddrName(userEntity.getAddrName());
                userDTO.setTenantId(userEntity.getTenantId());
                userDTO.setName(userEntity.getName());
                dataResult.add(userDTO);
            }
        }
        result.setData(dataResult);
        return result;
    }
}
