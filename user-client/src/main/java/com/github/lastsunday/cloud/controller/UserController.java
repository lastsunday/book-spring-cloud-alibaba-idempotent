package com.github.lastsunday.cloud.controller;

import com.github.lastsunday.cloud.config.NacosConfig;
import com.github.lastsunday.cloud.service.DistributedService;
import com.github.lastsunday.cloud.service.GoodService;
import com.github.lastsunday.cloud.service.UserService;
import com.github.lastsunday.cloud.service.dto.UserDTO;
import com.github.lastsunday.cloud.service.request.UserServiceRequest;
import com.github.lastsunday.cloud.service.response.DefaultResult;
import com.github.lastsunday.cloud.service.response.Result;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value="/user")
public class UserController {

    @DubboReference(version = "1.0.0", group = "idempotent-user-server", retries = 0)
    private UserService userService;

    @DubboReference(version = "1.0.0", group = "distributed-uuid-server")
    private DistributedService distributedService;

    @Autowired
    private NacosConfig nacosConfig;

    @GetMapping(value="/getUserInfo")
    @ResponseBody
    public Result<List<UserDTO>> getUserInfo(@RequestParam String tenantId,@RequestParam String userName){
        DefaultResult<List<UserDTO>> result = new DefaultResult<>();
        UserServiceRequest userServiceRequest = new UserServiceRequest();
        userServiceRequest.setRequestData("test getUserInfo");
        userServiceRequest.setTenantId(tenantId);
        userServiceRequest.setUsername(userName);
        if(nacosConfig.isMideng()){
            long uuid = distributedService.nextId();
            userServiceRequest.setUuid(uuid+"");
            //模拟幂等性场景-超时重试 100次
            for(int i=0;i<100;i++){
                result = userService.getUserInfo(userServiceRequest);
            }
        }else{
            result = userService.getUserInfo(userServiceRequest);
        }
        return result;
    }

}
