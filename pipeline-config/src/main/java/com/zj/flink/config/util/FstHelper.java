package com.zj.flink.config.util;

import org.apache.fury.Fury;
import org.apache.fury.ThreadLocalFury;
import org.apache.fury.ThreadSafeFury;
import org.apache.fury.config.Language;

import java.util.Base64;

/**
 *
 */
public class FstHelper {
    private static final ThreadSafeFury fury = new ThreadLocalFury((classLoader) -> Fury.builder().withLanguage(Language.JAVA).withClassLoader(classLoader).requireClassRegistration(false).build());

    public FstHelper() {
    }

    public static String obj2String(Object o) {
        byte[] bytes = fury.serialize(o);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }

    public static <T> T string2Obj(String s) {
        byte[] decode = Base64.getUrlDecoder().decode(s);
        return (T) fury.deserialize(decode);
    }
}
