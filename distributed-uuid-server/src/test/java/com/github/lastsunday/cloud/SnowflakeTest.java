package com.github.lastsunday.cloud;

import com.github.lastsunday.cloud.config.service.impl.SnowFlake;
import org.junit.Test;

public class SnowflakeTest {

    @Test
    public void testSnowFlake(){
        SnowFlake snowFlake = new SnowFlake(0,0);
        for(int i=0;i<10;i++){
            System.out.println(snowFlake.nextId());
        }
    }
}
