package com.idanchuang.cms.server.interfaces.web.config;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:01
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class RequestContext {

    private static ThreadLocal<GatewayUserDTO> threadLocal = new ThreadLocal<>();

    public static void put(GatewayUserDTO dto) {
        threadLocal.set(dto);
    }

    public static GatewayUserDTO get() {
        return threadLocal.get();
    }

    public static Integer getUserId() {
        return get() == null ? null : get().getId();
    }

    public static String getRealName() {
        return get() == null ? null : get().getRealName();
    }

    public static void clean() {
        threadLocal.remove();
    }
}
