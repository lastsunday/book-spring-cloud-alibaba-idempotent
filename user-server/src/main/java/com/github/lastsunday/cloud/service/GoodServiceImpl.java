package com.github.lastsunday.cloud.service;

import com.github.lastsunday.cloud.bo.ProductBO;
import com.github.lastsunday.cloud.entity.ProductEntity;
import com.github.lastsunday.cloud.mapper.ProductMapper;
import com.github.lastsunday.cloud.service.dto.GoodDTO;
import com.github.lastsunday.cloud.service.request.GoodServiceRequest;
import com.github.lastsunday.cloud.service.response.DefaultResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@DubboService(version = "1.0.0", group = "idempotent-user-server")
@Slf4j
public class GoodServiceImpl implements GoodService{

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public DefaultResult<GoodDTO> updateGoodNum(GoodServiceRequest goodServiceRequest) {
        DefaultResult<GoodDTO> result = new DefaultResult<GoodDTO>();
        if(!StringUtils.isEmpty(goodServiceRequest.getUuid())){
            long uuid = Long.valueOf(goodServiceRequest.getUuid());
            if (null != redisTemplate.opsForValue().get(uuid)) {
                result.setData(new GoodDTO());
                result.setMessage("uuid:" + uuid + "already access multiple times;");
                return result;
            } else {
                redisTemplate.opsForValue().set(uuid,true,2, TimeUnit.SECONDS);
            }
        }
        ProductBO productBO = (ProductBO) goodServiceRequest.getRequestData();
        try {
            //设置执行延时时间2s
            Thread.sleep(2000);
        }catch (InterruptedException e){
            log.error(e.getMessage(),e);
        }
        List<ProductEntity> queryResult1 =  productMapper.queryGoodInfoByGoodId(productBO);
        if(!CollectionUtils.isEmpty(queryResult1)){
            ProductEntity item = queryResult1.get(0);
            log.info("开始扣减库存，扣除之前的商品库存为："+item.getNum()+" 商品ID为："+item.getGoodId());
        }
        productMapper.updateGoodNum(productBO);
        List<ProductEntity> queryResult2 =  productMapper.queryGoodInfoByGoodId(productBO);
        if(!CollectionUtils.isEmpty(queryResult2)) {
            ProductEntity item = queryResult2.get(0);
            log.info("开始扣减库存，扣除之后的商品库存为：" + item.getNum() + " 商品ID为：" + item.getGoodId());
        }
        GoodDTO returnItem = new GoodDTO();
        returnItem.setGoodId(queryResult2.get(0).getGoodId());
        returnItem.setNum(queryResult2.get(0).getNum());
        result.setData(returnItem);
        result.setCode(DefaultResult.SUCCESS_CODE);
        result.setMessage("库存扣减成功！！！！！");
        return result;
    }
}
