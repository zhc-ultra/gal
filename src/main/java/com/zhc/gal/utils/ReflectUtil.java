package com.zhc.gal.utils;

import com.zhc.gal.annotation.CorrectMethod;
import com.zhc.gal.annotation.Validator;
import com.zhc.gal.annotation.ValidatorMethod;
import com.zhc.gal.core.GalContext;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Set;

/**
 * @author zhc
 * @description
 * @date 2024/5/13 14:49
 **/
public class ReflectUtil {
    public static Type getGenericReturnType(Method method) {
        method.setAccessible(true);
        return method.getGenericReturnType();
    }

    /**
     * 根据类和方法名获取方法
     *
     * @param clazz      类对象
     * @param methodName 方法名
     * @return 返回匹配的方法，如果没有匹配的方法则返回null
     */
    public static Method getMethod(Class<?> clazz, String methodName) {
        return // 1. 使用Java 8的流式API来获取和筛选方法
                Arrays.stream(clazz.getDeclaredMethods()).
                        // 2. 使用instanceof操作符来匹配方法名
                                filter(method -> methodName.equals(method.getName())).
                        // 3. 如果找到了匹配的方法，则返回该方法
                                findFirst().orElse(null);
    }

    /**
     * 根据完整的类名字符串获取无参实例对象。
     *
     * @param fullClassNameStr 完整的类名字符串，格式为 "fully.qualified.ClassName"。
     * @return 无参实例对象
     * @throws ClassNotFoundException 当找不到指定的类时抛出此异常
     * @throws NoSuchMethodException  当找不到指定的方法时抛出此异常
     * @throws InstantiationException 当类是抽象类、接口或数组类时或发生其他不可逆错误时抛出此异常
     * @throws IllegalAccessException 当类或类成员处于访问冲突中时抛出此异常
     */
    public static Object getNonParameterInstance(String fullClassNameStr) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // 参数校验：确保类名字符串非空
        if (fullClassNameStr == null || fullClassNameStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to instantiate the collection");
        }
        // 获取类对象
        Class<?> clazz = Class.forName(fullClassNameStr);
        // 获取无参构造函数
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        // 允许调用私有构造函数
        constructor.setAccessible(true);
        return constructor.newInstance();
    }


    /**
     * 使用反射调用类的方法
     *
     * @param args 方法的参数列表
     * @return 方法的返回值
     */
    public static Object invoke(Method method, Object... args) {
        Class<?> clazz = GalContext.clazz;

        try {
            // 反射获取构造方法
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object o = constructor.newInstance();
            // 调用方法
            return method.invoke(o, args);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            // 更详细的错误信息
            String errorMsg = "Failed to invoke method: " + method.getName() + " in class: " + clazz.getName() +
                    ". Exception: " + e.getMessage();
            System.out.println(errorMsg);
            throw new RuntimeException(errorMsg, e);
        }
    }


    public static Method reflectAnnotationMethod(Class<? extends Annotation> annotation) {
        Class<?> clazz = GalContext.clazz;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                return method;
            }
        }
        return null;
    }

    public static Class<?> scanValidatorAnnotation(String packageName) {
        // 配置Reflections
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName))
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()));

        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Validator.class);

        if (annotatedClasses.size() > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            annotatedClasses.forEach((cls) -> {
                stringBuilder.append(cls.getName()).append(" ");
            });
            throw new RuntimeException("Found more than one @Validator annotated" + "\n" + stringBuilder);
        }

        return annotatedClasses.iterator().next();
    }

    /**
     * 根据给定的Type类型获取对应的泛型数组签名字符串
     *
     * @param type 泛型类型
     * @return 对应的泛型数组签名字符串
     */
    public static String getGenericArraySignatureByType(Type type) {
        assert type instanceof GenericArrayType;

        StringBuilder s = new StringBuilder();
        String fullClassName = type.getTypeName();
        String dimensions = type.getTypeName();
        // 获取泛型参数开始的位置
        for (int i = 0; i < fullClassName.length(); i++) {
            if ('<' != fullClassName.charAt(i)) {
                s.append(fullClassName.charAt(i));
            } else {
                break;
            }
        }
        int count = 0;
        // 去掉维度为数组的中括号，保留类名之前的空数组中括号
        for (int i = dimensions.length() - 1; i >= 0; i -= 2) {
            if (']' == dimensions.charAt(i) && '[' == dimensions.charAt(i - 1)) {
                count++;
            } else {
                break;
            }
        }
        StringBuilder ret = new StringBuilder();
        // 添加维度为数组的中括号
        for (int i = 0; i < count; i++) {
            ret.append("[");
        }
        ret.append("L");
        // full class name
        ret.append(s);
        ret.append(";");
        return ret.toString();
    }
}
