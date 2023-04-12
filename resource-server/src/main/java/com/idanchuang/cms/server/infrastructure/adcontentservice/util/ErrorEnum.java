package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.core.IErrorEnum;

/**
 * @author shouyika
 */
public enum ErrorEnum implements IErrorEnum {

    SUCCESS(0, "请求成功"),
    BAD_REQUEST(1010, "请求参数错误或不完整"),
    SYSTEM_BUSY(3900, "系统繁忙，请稍后再试"),
    UNKNOWN_ERROR(3010, "未知错误"),
    GOODS_INFO_ERROR(1302, "商品信息服务异常"),
    COMMON_REQUEST_ERROR(4000, "{}"),
    ID_NOT_EXIST(1009, "ID不能为空"),
    HOT_AREA_TIME_CROSS(2203, "同一平台同一个位置同一行同一列投放时间不可交叉"),
    DISPLAY_LINE_EXCEED(2202, "行数已满50无法插入，请删除50行再保存"),
    QUERY_AD_CONTENT_ERROR(1210, "热区服务异常");

    private int code;
    private String msg;

    ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public ExType getExType() {
        return ExType.BUSINESS_ERROR;
    }

    public static String getByMessageByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ErrorEnum obe : values()) {
            if (code.equals(obe.getCode())) {
                return obe.getMsg();
            }
        }
        return null;
    }


}
