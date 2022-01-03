package com.github.lastsunday.cloud.config.service.impl;

import com.github.lastsunday.cloud.config.SnowflakeConfig;
import com.github.lastsunday.cloud.config.SnowflakeInfo;
import com.github.lastsunday.cloud.service.DistributedService;
import com.github.lastsunday.cloud.util.NetUtils;
import com.google.common.base.Objects;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@DubboService(version = "1.0.0",group = "distributed-uuid-server")
public class DistributedServiceImpl implements DistributedService {

    @Autowired
    private SnowflakeConfig snowflakeConfig;
    Map<String,SnowFlake> snowFlakeHandlerMap = new ConcurrentHashMap<>();

    @Override
    public long nextId(long datacenterId, long machineId) {
        final long sdatacenterId = datacenterId;
        final long smachineId = machineId;
        final String handler = sdatacenterId+"_"+smachineId;
        SnowFlake snowFlake;
        if(snowFlakeHandlerMap.containsKey(handler)){
            snowFlake = snowFlakeHandlerMap.get(handler);
            return snowFlake.nextId();
        }else {
            snowFlake = new SnowFlake(datacenterId,machineId);
            snowFlakeHandlerMap.put(handler,snowFlake);
            snowFlake = snowFlakeHandlerMap.get(handler);
            return snowFlake.nextId();
        }
    }

    @Override
    public long nextId() {
        List<SnowflakeInfo> config = snowflakeConfig.getConfig();
        String localAddress = NetUtils.getLocalAddress();
        SnowflakeInfo snowflakeInfo = config.stream().filter(s-> Objects.equal(s.getIp(),localAddress)).findFirst().orElse(null);
        long dataCenterId = Optional.ofNullable(snowflakeInfo).map(SnowflakeInfo::getDataCenterId).orElse(0L);
        long machineId = Optional.ofNullable(snowflakeInfo).map(SnowflakeInfo::getDataCenterId).orElse(0L);
        return nextId(dataCenterId,machineId);
    }
}
