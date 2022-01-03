package com.github.lastsunday.cloud.service.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserServiceRequest <T> implements Serializable {

    private T requestData;
    private String productId;
    private String tenantId;
    private String uuid;
    private String username;
}
