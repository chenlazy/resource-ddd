package com.idanchuang.cms.server.infrastructureNew.external.feign;

import com.google.common.collect.Maps;
import com.idanchuang.component.redis.util.RedisUtil;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author fym
 * @description :
 * @date 2022/4/26 下午1:32
 */
@Slf4j
public class GoodsTagExternalServiceTest extends SpringTest {

    @Test
    public void expireOldTag() {
        try {
            Map<Long, Long> maps = Maps.newHashMap();
            maps.put(123L, 1234L);
            String str = "11111111111";
            RedisUtil.getInstance().setObj(str, maps);
            Thread.currentThread().sleep(1000);
            Map<Long, Long> map = RedisUtil.getInstance().getObj(str);
            System.out.println(map);
        } catch (Exception e) {
            log.error("error e:{}", e);
        }

    }
}