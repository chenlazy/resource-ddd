package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

/**
 * @author xf
 * @Date 2020-12-31
 */
public enum AuditEnum {
    /**
     * 审核中
     */
    AUDIT_IN(0, "审核中"),
    /**
     * 审核成功
     */
    AUDIT_SUCCESS(1, "审核成功"),
    /**
     * 审核失败
     */
    AUDIT_FAIL(2, "审核失败");

    private Integer value;
    private String desc;

    AuditEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }



}
