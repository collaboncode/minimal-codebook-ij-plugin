package com.codingchapters.isplugin;

import java.lang.reflect.Field;

public interface PrivateAccess {

    default Object readPrivateField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return readPrivateFieldOfObject(obj, obj.getClass(), fieldName);
    }

    private Object readPrivateFieldOfObject(Object obj, Class<?> klass, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = klass.getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(obj);
    }

    default void writeSuperPrivateField(Object obj, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        writePrivateField(obj, obj.getClass().getSuperclass(), fieldName, fieldValue);
    }

    private void writePrivateField(Object obj, Class<?> klass, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        Field f = klass.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(obj, fieldValue);
    }
}
