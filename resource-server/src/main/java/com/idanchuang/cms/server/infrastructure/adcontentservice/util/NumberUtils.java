package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhousun
 * @create 2020/11/27
 */
public class NumberUtils {

    public static List<Long> convertToLong(List<Integer> idList) {
        if(CollectionUtils.isEmpty(idList)) {
            return Lists.newArrayList();
        }
        return idList.stream().map(Integer::longValue).collect(Collectors.toList());
    }

    public static boolean isInteger(BigDecimal decimal){
        if (new BigDecimal(decimal.intValue()).compareTo(decimal)==0){
            return true;
        }else {
            return false;
        }
    }

}
