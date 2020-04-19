package com.wrapper.proxyapplication;

import android.content.Context;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ShareReflectUtil {
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    public static Field findField(Class<?> originClazz, String name) throws NoSuchFieldException {
        Class<?> clazz = originClazz;
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + originClazz);
    }

    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + clazz);
    }

    public static Constructor<?> findConstructor(Object instance, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            try {
                Constructor<?> ctor = clazz.getDeclaredConstructor(parameterTypes);
                if (!ctor.isAccessible()) {
                    ctor.setAccessible(true);
                }
                return ctor;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Constructor with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    public static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(extraElements, 0, combined, 0, extraElements.length);
        System.arraycopy(original, 0, combined, extraElements.length, original.length);
        jlrField.set(instance, combined);
    }

    public static void reduceFieldArray(Object instance, String fieldName, int reduceSize) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (reduceSize > 0) {
            Field jlrField = findField(instance, fieldName);
            Object[] original = (Object[]) jlrField.get(instance);
            int finalLength = original.length - reduceSize;
            if (finalLength > 0) {
                Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), finalLength);
                System.arraycopy(original, reduceSize, combined, 0, finalLength);
                jlrField.set(instance, combined);
            }
        }
    }

    public static Object getActivityThread(Context context, Class<?> activityThread) {
        if (activityThread == null) {
            try {
                activityThread = Class.forName("android.app.ActivityThread");
            } catch (Throwable th) {
                return null;
            }
        }
        Method m = activityThread.getMethod("currentActivityThread", new Class[0]);
        m.setAccessible(true);
        Object currentActivityThread = m.invoke(null, new Object[0]);
        if (currentActivityThread != null || context == null) {
            return currentActivityThread;
        }
        Field mLoadedApk = context.getClass().getField("mLoadedApk");
        mLoadedApk.setAccessible(true);
        Object apk = mLoadedApk.get(context);
        Field mActivityThreadField = apk.getClass().getDeclaredField("mActivityThread");
        mActivityThreadField.setAccessible(true);
        return mActivityThreadField.get(apk);
    }

    public static int getValueOfStaticIntField(Class<?> clazz, String fieldName, int defVal) {
        try {
            return findField(clazz, fieldName).getInt(null);
        } catch (Throwable th) {
            return defVal;
        }
    }
}
