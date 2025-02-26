package com.zj.flink.common.beans;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserInfoData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String name;
    private String sex;
    private Integer age;
}
