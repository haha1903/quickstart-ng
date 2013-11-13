package com.datayes.cloud.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public static Map toMap(InputStream is) throws IOException {
        return mapper.readValue(is, Map.class);
    }

    public static <T> T toObject(InputStream is, TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(is, typeReference);
    }

    public static <T> T toObject(InputStream is, JavaType javaType) throws IOException {
        return mapper.readValue(is, javaType);
    }

    public static <T> T toObject(String json, TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(json, typeReference);
    }

    public static <T> T toObject(String json, JavaType type) throws IOException {
        return mapper.readValue(json, type);
    }
}
