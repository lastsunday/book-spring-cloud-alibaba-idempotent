package com.github.lastsunday.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.lastsunday.cloud.bo.ProductBO;
import com.github.lastsunday.cloud.entity.ProductEntity;

import java.util.List;

public interface ProductMapper extends BaseMapper<ProductEntity> {

    List<ProductEntity> queryGoodInfoByGoodId(ProductBO productBO);

    Integer updateGoodNum(ProductBO productBO);

}
