package com.github.lastsunday.cloud.service;

import com.github.lastsunday.cloud.service.dto.GoodDTO;
import com.github.lastsunday.cloud.service.request.GoodServiceRequest;
import com.github.lastsunday.cloud.service.response.DefaultResult;

public interface GoodService {
    DefaultResult<GoodDTO> updateGoodNum(GoodServiceRequest goodServiceRequest);
}
