package com.zhc.gal.core;

import com.zhc.gal.annotation.CorrectMethod;
import com.zhc.gal.annotation.Validator;
import com.zhc.gal.annotation.ValidatorMethod;
import com.zhc.gal.utils.ReflectUtil;
import lombok.Data;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author zhc
 * @description 对数器上下文
 * @date 2024/5/13 15:36
 **/
@Data
public class GalContext {
    public static Class<?> clazz = null;
    private Method validatorMethod = null;
    private Method correctMethod = null;
    private Integer testTimes;
    private final List<Parameter> parameters;

    public GalContext(List<Parameter> parameters) {
        this.parameters = parameters;
        setCorrectMethod(ReflectUtil.reflectAnnotationMethod(ValidatorMethod.class));
        setValidatorMethod(ReflectUtil.reflectAnnotationMethod(CorrectMethod.class));
        Validator configAnnotation = clazz.getAnnotation(Validator.class);
        setTestTimes(configAnnotation.count());
    }

}