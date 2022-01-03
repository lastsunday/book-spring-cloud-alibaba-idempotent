package com.github.lastsunday.cloud.controller;

import com.github.lastsunday.cloud.bo.ProductBO;
import com.github.lastsunday.cloud.config.NacosConfig;
import com.github.lastsunday.cloud.service.DistributedService;
import com.github.lastsunday.cloud.service.GoodService;
import com.github.lastsunday.cloud.service.dto.GoodDTO;
import com.github.lastsunday.cloud.service.request.GoodServiceRequest;
import com.github.lastsunday.cloud.service.response.DefaultResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/good")
public class GoodController {

    @DubboReference(version = "1.0.0", group = "idempotent-user-server", retries = 4, timeout = 1)
    private GoodService goodService;

    @DubboReference(version = "1.0.0", group = "distributed-uuid-server")
    private DistributedService distributedService;

    @Autowired
    private NacosConfig nacosConfig;

    @PostMapping(value="/updateGoodNum")
    @ResponseBody
    public DefaultResult<GoodDTO> updateGoodNum(@RequestParam String goodId) {
        GoodServiceRequest<ProductBO> goodServiceRequest = new GoodServiceRequest<>();
        ProductBO productBO = new ProductBO();
        productBO.setGoodId(Long.valueOf(goodId));
        goodServiceRequest.setRequestData(productBO);
        if (nacosConfig.isMideng()) {
            long uuid = distributedService.nextId();
            goodServiceRequest.setUuid(uuid + "");
        }
        return goodService.updateGoodNum(goodServiceRequest);
    }
}
