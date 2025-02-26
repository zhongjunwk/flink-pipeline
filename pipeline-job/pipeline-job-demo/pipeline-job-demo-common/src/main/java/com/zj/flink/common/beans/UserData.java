package com.zj.flink.common.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String name;
    private String sex;
    private Integer age;
}