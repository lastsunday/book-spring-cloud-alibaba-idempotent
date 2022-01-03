package com.github.lastsunday.cloud.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SnowflakeInfo {
    private String ip;
    private Long dataCenterId;
    private Long machineId;
}
