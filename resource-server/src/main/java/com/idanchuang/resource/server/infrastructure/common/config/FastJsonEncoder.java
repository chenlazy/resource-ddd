package com.idanchuang.resource.server.infrastructure.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author wyh
 * @date 2020/4/14
 */
@Component("fastJsonEncoder")
public class FastJsonEncoder implements Function<Object, byte[]> {

    @Override
    public byte[] apply(Object o) {
        if (o == null) {
            return new byte[0];
        }
        try {

            return JSON.toJSONBytes(o, new SerializerFeature[]{SerializerFeature.WriteClassName});

        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

}
