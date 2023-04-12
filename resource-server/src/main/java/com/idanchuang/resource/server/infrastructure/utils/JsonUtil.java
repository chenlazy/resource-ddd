package com.idanchuang.resource.server.infrastructure.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.idanchuang.resource.server.infrastructure.common.exception.JsonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by develop at 2021/2/5.
 *
 * @author fym
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    private JsonUtil() {
        //
    }

    public static <K, V> Map<K, V> toMap(String json, Type type) {
        TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new JsonException(json, e.toString());
        }
    }

    public static <K, V> Map<K, V> toMapErrorNull(String json, Type type) {
        TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.error("toMapErrorNull error json:{} e:{}", json, e.toString());
            return null;
        }
    }

    public static <T> List<T> toList(String json, Type type) {

        if(StringUtils.isBlank(json)){
            return Lists.emptyList();
        }

        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new JsonException(json, e.toString());
        }
    }

    public static <T> List<T> toListErrorNull(String json, Type type) {
        if (StringUtils.isBlank(json)) {
            return Lists.emptyList();
        }
        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("toListErrorNull error json:{} e:{}", json, e.toString());
            return null;
        }
    }

    public static <T> List<T> toList(String json, TypeReference typeReference) {
        if (StringUtils.isBlank(json)) {
            return Lists.emptyList();
        }
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("toList error json:{} e:{}", json, e);
            throw new JsonException(json, e.toString());
        }
    }

    public static <T> List<T> toListErrorNull(String json, TypeReference typeReference) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("toListErrorNull error json:{} e:{}", json, e);
            return null;
        }
    }

    public static String toJsonString(Object object) {
        if(object == null){
            return "";
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJsonString error json:{} e:{}", object, e);
            throw new JsonException(object.toString(), e.toString());
        }
    }

    public static String toJsonStringErrorEmpty(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJsonStringErrorEmpty error json:{} e:{}", object.toString(), e.toString());
            return "";
        }
    }

    public static String toJsonString(Object object, String dateFormatPattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        try {
            return objectMapper.writer(dateFormat).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonException(object.toString(), e.toString());
        }
    }

    public static String toJsonStringErrorEmpty(Object object, String dateFormatPattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        try {
            return objectMapper.writer(dateFormat).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJsonStringErrorEmpty error json:{} e:{}", object.toString(), e.toString());
            return "";
        }
    }

    public static <T> T toObject(String json, Class<T> valueType) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            throw new JsonException(json, e.toString());
        }
    }

    public static <T> T toObjectErrorNull(String json, Class<T> valueType) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            log.error("toObjectErrorNull error json:{} e:{}", json, e.toString());
            return null;
        }
    }
}
