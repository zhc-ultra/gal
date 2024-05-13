package com.zhc.gal.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhc
 * @description 比价输出是否相同，先将输出转化为Json字符串，再对字符串进行比较
 * @date 2024/5/13 14:41
 **/
public class DepthEquals {
    public static ObjectMapper jsonMapper = new ObjectMapper();
    public static boolean deepEquals(Object output1, Object output2) throws JsonProcessingException {
        String outputStr1 = jsonMapper.writeValueAsString(output1);
        String outputStr2 = jsonMapper.writeValueAsString(output2);
        return outputStr1.equals(outputStr2);
    }
}