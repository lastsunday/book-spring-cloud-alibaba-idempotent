package com.github.lastsunday.cloud.service;

import com.github.lastsunday.cloud.service.dto.UserDTO;
import com.github.lastsunday.cloud.service.request.UserServiceRequest;
import com.github.lastsunday.cloud.service.response.DefaultResult;

import java.util.List;

public interface UserService {

    DefaultResult<List<UserDTO>> getUserInfo(UserServiceRequest userServiceRequest);
}
