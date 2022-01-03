package com.github.lastsunday.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("product")
public class ProductEntity implements Serializable {

    @TableId(value="id",type= IdType.AUTO)
    private Long id;
    private Long goodId;
    private String goodName;
    private Long num;
    @Version
    private Integer version;
}
