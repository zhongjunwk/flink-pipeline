package com.idss.titan.ruleengine;

/**
 * User: cch
 * Date: 2021/1/11 16:16
 * Description:规则引擎返回结果
 */
public class RuleEngineResult {

    /**
     * 规则id
     */
    public long id;

    public String tagCode;

    /**
     * 规则名称
     */
    public String name;

    /**
     * 敏感数据
     */
    public SensitiveData[] data;

}
