package com.idss.titan.ruleengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: cch
 * Date: 2021/1/11 16:05
 * Description:规则引擎
 */
public class RuleEngine {
    private static Logger logger = LoggerFactory.getLogger(RuleEngine.class);

    static {
        try {
            System.loadLibrary("ruleengineapi");
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }

    }


    /**
     * 加载规则
     * 目前规则引擎编译完后返回结果 耗时长
     *
     * @param ruleContent 全量规则内容
     * @return
     */
    public static native int loadRules(String ruleContent);

    /**
     * 加载字典
     * 目前规则引擎编译完后返回结果 耗时长
     *
     * @param dicContent 字典内容
     * @return
     */
    public static native int loadDictionaries(String dicContent);

    /**
     * 匹配规则
     *
     * @param content-数据json字符串 extern_content-额外信息
     * @return
     */
    public static native RuleEngineResult[] scanData(String content, String extern_content);


    /**
     * 测试规则
     *
     * @param ruleContent 当前规则内容
     * @param dicContent  当前引用的字典
     * @param content     验证的数据
     * @return
     */
    public static native RuleEngineResult[] testData(String ruleContent, String dicContent, String content, String extern_content);

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

}
