package com.zhc.gal.input;

import com.zhc.gal.Pairs;
import com.zhc.gal.annotation.ValidatorMethod;
import com.zhc.gal.core.GalContext;
import com.zhc.gal.exception.UnableResolveTypeException;
import com.zhc.gal.exception.UnknownTypeException;
import com.zhc.gal.utils.ConfigUtil;
import com.zhc.gal.utils.ReflectUtil;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author zhc
 * @description 解析函数签名
 * @date 2024/5/13 14:40
 **/
public class ParseMethodSignature {
    public static int[] times = new int[9];
    public static Map<Pairs, Parameter> parameters = new HashMap<>();

    public static List<Parameter> parseMethodSignature() throws UnknownTypeException, ClassNotFoundException {
        String packageName = ConfigUtil.readPackageName();
        GalContext.clazz = ReflectUtil.scanValidatorAnnotation(packageName);
        Method method = ReflectUtil.reflectAnnotationMethod(ValidatorMethod.class);
        assert method != null;
        Type[] types = method.getGenericParameterTypes();
        List<Parameter> parameters = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            parameters.add(new Parameter());
            // 当前为第 i 个参数
            parameters.get(i).setI(i);
            // 第一层深度为 0
            parameters.get(i).setJ(0);
            parameters.get(i).setType(types[i]);
        }

        for (int i = 0; i < types.length; i++) {
            Arrays.fill(times, 0);
//            parserDispatcher(parameters.get(i));
        }
        return parameters;
    }
}