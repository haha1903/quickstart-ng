package com.datayes.cloud.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午5:39
 * DataYes
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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

    public static <T> T toObject(String content, TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(content, typeReference);
    }
}
