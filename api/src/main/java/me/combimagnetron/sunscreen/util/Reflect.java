package me.combimagnetron.sunscreen.util;

import java.lang.reflect.Field;

public class Reflect {

    public static <T> T field(Object t, String fieldName) {
        try {
            Field field = t.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(t);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

}
