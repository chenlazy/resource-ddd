package com.idanchuang.cms.server.application.enums;

import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.core.IErrorEnum;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-18 11:21
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public enum ErrorEnum implements IErrorEnum {

    /**
     * 错误标记
     */
    SUCCESS(0, "请求成功"),
    BAD_REQUEST(1010, "请求参数错误或不完整"),
    SYSTEM_BUSY(3900, "系统繁忙，请稍后再试"),
    UNKNOWN_ERROR(3010, "未知错误"),
    COMMON_REQUEST_ERROR(4000, "{}"),
    DISPLAY_LINE_EXCEED(2202, "行数已满50无法插入，请删除50行再保存"),
    HOT_AREA_TIME_CROSS(2203, "同一平台同一个位置同一行同一列投放时间不可交叉"),
    ID_NOT_EXIST(1009, "ID不能为空"),
    GOODS_TAG_ERROR(5000, ""),
    GOODS_INFO_ERROR(1302, "商品信息服务异常"),
    PAGE_SCHEMA_ERROR(1303, "页面定义不存在"),
    CONTAINER_NO_EXIST(1304, "查询的容器不存在"),
    PAGE_TEMPLATE_ERROR(1305, "页面模版不存在"),
    PAGE_VALID_ERROR(1306, "最后一个模版不能删除"),
    SCHEMA_CODE_EXIST(1307, "页面pageCode已经存在"),
    CONTAINER_CODE_REPEAT(1308, "页面容器code重复"),
    CATALOGUE_EXIST_ERROR(1309, "目录不存在"),
    MASTERPLATE_EXIST_ERROR(1310, " 模版不存在"),
    PAGE_STYLE_PARSE_ERROR(1311, "前端样式解析出错"),
    PAGE_TITLE_EMPTY_ERROR(1312, "页面标题不能为空"),
    PAGE_ID_EMPTY_ERROR(1313, "页面id不能为空"),
    START_TIME_EMPTY_ERROR(1314, "开始时间不能为空"),
    MASTERPLATE_NAME_NOT_EMPTY(1315, "模版名称不能为空"),
    CONTAINER_RELATION_ERROR(1316, "该容器不是该模版下的"),
    MASTERPLATE_CONTAINER_ERROR(1317, "该模版不属于该目录下"),
    CLIENT_PAGE_EXIST_ERROR(1318, "该页面code已存在"),
    CLIENT_PAGE_NOT_EXIST_ERROR(1319, "页面code不存在"),
    CATALOGUE_TYPE_EXIST_ERROR(1320, "页面类型不存在"),
    CLIENT_PAGE_TYPE_EXIST_ERROR(1321, "该原生页面类型已存在"),
    COMPONENT_INFO_EXIST_ERROR(1322, "组件信息已存在"),
    CATALOGUE_ID_EXIST_ERROR(1323, "页面id不存在，请联系开发"),
    EXIST_DUPLICATE_KEY(1324, "组件id存在重复key，请联系开发"),
    HISTORY_PAGE_DATA_NOT_EXIST(1325, "当前模版不存在历史数据"),
    COMPONENT_ID_NOT_EXIST_ERROR(1326, "组件id未生成，请在清除缓存后复制该模版再发布");



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
