package com.zhc.gal.input;

import com.zhc.gal.Pairs;
import com.zhc.gal.annotation.ValidatorMethod;
import com.zhc.gal.core.GalContext;
import com.zhc.gal.utils.ConfigUtil;
import com.zhc.gal.utils.ReflectUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhc
 * @description 解析函数签名
 * @date 2024/5/13 14:40
 **/
public class ParseMethodSignature {
    public static int[] times = new int[9];
    public static Map<Pairs, Parameter> parameters = new HashMap<>();

    public static List<Parameter> parseMethodSignature() {
        String packageName = ConfigUtil.readPackageName();
        GalContext.clazz = ReflectUtil.scanValidatorAnnotation(packageName);
        Method method = ReflectUtil.reflectAnnotationMethod(ValidatorMethod.class);
        assert method != null;

        Type[] types = method.getGenericParameterTypes();

        return null;
    }
}