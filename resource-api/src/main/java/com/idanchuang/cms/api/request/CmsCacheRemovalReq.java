package com.idanchuang.cms.api.request;

import lombok.Data;

import java.util.Map;

/**
 * @author fym
 * @description :
 * @date 2021/9/17 上午10:17
 */
@Data
public class CmsCacheRemovalReq {

    private Map<Long, String> goodsMap;

    private Map<Long, String> tagMap;

}
