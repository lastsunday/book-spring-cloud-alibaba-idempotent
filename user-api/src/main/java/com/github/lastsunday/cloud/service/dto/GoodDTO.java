package com.github.lastsunday.cloud.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodDTO implements Serializable {
    private Long id;
    private Long goodId;
    private String goodName;
    private Long num;
}
