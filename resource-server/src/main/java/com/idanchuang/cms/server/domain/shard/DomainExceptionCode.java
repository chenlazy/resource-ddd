package com.idanchuang.cms.server.domain.shard;

public enum DomainExceptionCode {
    ID_SHOULD_BE_POSITIVE("ID必须大于0"),
    ID_SHOULD_BE_EXIST("ID不能为空"),
    DATA_NOT_FOUND("找不到%s数据 ID:%s"),
    BUSINESS_ERROR("%s"),
    INVALID_DATA("数据异常 %s"),
    DATA_NOT_EXIST("%s数据不存在"),
    PRICE_SHOULD_BE_POSITIVE("价格不能小于0"),
    CAST_OBJECT_TO_JSON_FAIL("Cast object to json fail"),
    NOT_SUPPORT_OPERATION("不支持该操作 %s"),
    EXECUTE_FAIL("执行失败：%s"),
    ORDER_STATUS_CHANGE_FAIL("当前订单状态%s,不支持变更到%s状态"),
    ORDER_PAY_AMOUNT_VALID_FAIL("订单支付金额校验失败,订单id%s,原订单支付金额%s,渠道支付金额%s"),
    REFUND_AMOUNT_VALID_FAIL("退款单退款金额校验失败,退款单id%s,原退款单退款金额%s,渠道退款金额%s"),
    REFUND_CAUSE_NOT_BE_EMPTY("退款原因不能为空"),
    ORDERLINES_NOT_FOUND("订单:%s上未找到指定门票sku:%s的订单项信息"),
    REFUND_CHANNEL_UNKNOWN("退款渠道未知"),
    USER_TICKET_STATUS_CHANGE_FAIL("当前用户门票状态%s,不支持变更到%s状态"),
    QR_CODE_GENERAL_FAIL("二维码生成失败"),
    SKU_INVALID_DATA("Excel表SkuId不正确：%s"),


    ACTIVITY_USER_NOT_FOUNT("用户不存在"),
    ACTIVITY_START_TIME("活动未开始"),
    ACTIVITY_END_TIME("活动已结束"),
    PURCHASE_GRADE_LIMIT("用户等级不支持购买"),
    PURCHASE_ID_CODE_LIMIT("该门票只有指定用户才能购买"),
    CAN_CIRCULATE("该活动不支持赠送"),
    PRESENT_GRADE_LIMIT("该用户等级不可被赠送"),
    PRESENT_ID_CODE_LIMIT("该门票只有指定用户才能被送赠"),


    ;

    private String message;

    DomainExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage(Object... param) {
        return String.format(message, param);
    }
}
