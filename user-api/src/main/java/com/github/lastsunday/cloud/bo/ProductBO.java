package com.github.lastsunday.cloud.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductBO implements Serializable {

    private Long id;
    private Long goodId;
    private String goodName;
    private Long num;
}
