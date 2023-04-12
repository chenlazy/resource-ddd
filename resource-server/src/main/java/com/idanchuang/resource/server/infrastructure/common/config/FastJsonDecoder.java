package com.idanchuang.resource.server.infrastructure.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author wyh
 * @date 2020/4/14
 */
@Component("fastJsonDecoder")
public class FastJsonDecoder implements Function<byte[], Object> {

    private static ParserConfig defaultConfig = new ParserConfig();

    static {
        defaultConfig.setAutoTypeSupport(true);
    }

    @Override
    public Object apply(byte[] bytes) {

        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {

            return JSON.parseObject(new String(bytes, IOUtils.UTF8), Object.class, defaultConfig);

        } catch (Exception ex) {
            throw new SerializationException("Could not deserialize: " + ex.getMessage(), ex);
        }
    }

}
