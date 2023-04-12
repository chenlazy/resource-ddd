package com.idanchuang.cms.api.common.enums;

import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.core.IErrorEnum;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-10-21 10:44
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public enum ErrorEnum implements IErrorEnum {

    /**
     * 错误码
     */
    SUCCESS(0, "请求成功"),
    BAD_REQUEST(1010, "请求参数错误或不完整"),
    SYSTEM_BUSY(3900, "系统繁忙，请稍后再试"),
    UNKNOWN_ERROR(3010, "未知错误"),
    COMMON_REQUEST_ERROR(4000, "{}"),
    USER_INFO_NOT_ERROR(4001,"用户信息不存在"),
    USER_ROLE_NOT_ERROR(4002,"用户角色不存在"),
    GOODS_TYPE_TO_ROLE_NOT_ERROR(4003,"商品类型对应查看角色不存在"),
    USER_PROVIDER_ERROR(1201, "查询用户代理商信息异常"),
    PROFIT_CALCULATE_ERROR(1202,"佣金服务异常"),
    QUERY_USER_ROLE_ERROR(1204,"用户角色服务异常"),
    MARQUEE_USER_AWARD_ERROR(1203,"用户中奖信息服务异常"),
    CUSTOM_PARAM_ERROR(1011, "{}"),
    ;

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
