package com.zhc.gal.input;

import com.zhc.gal.enums.ParameterTypeEnum;
import com.zhc.gal.utils.Range;
import lombok.Data;

import java.lang.reflect.Type;

/**
 * @author zhc
 * @description
 * @date 2024/5/13 15:10
 **/
@Data
public class Parameter {
    // 递归
    private Type type;
    // 解析类型模板时用来处理递进
    private Parameter[] child;
    // 全限定类型 or 数组签名 配合 ArgumentType 使用
    private String className;
    // 参数类型 - PRIMITIVE,WRAPPER,CLASS,ARRAY,MAP,COLLECTION
    private ParameterTypeEnum parameterType;

    // 坐标 用来定位
    private Integer i;    // 第 i 个参数
    private Integer j;    // 深度为 j
    private Integer k;    // 当前深度第k个集合

    // 集合大小，如果该字段为 -1，则size字段不生效，优先级高于size
    Integer fixSize;
    // 数据元素的取值范围
    private Range value;
    // 数据集合大小
    private Range size;

    // 针对String做出的适配  字符有哪些字符构成
    private String chars;
    private Object[] values;

    // 排序规则
    private Order order;
}
