package com.github.lastsunday.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserEntity {
    private Long id;
    private Long tenantId;
    private String name;
    @TableField(exist = false)
    private String addrName;
}
