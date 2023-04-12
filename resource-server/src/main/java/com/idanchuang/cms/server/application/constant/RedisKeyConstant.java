package com.idanchuang.cms.server.application.constant;

/**
 * @author fym
 * @description :
 * @date 2021/3/16 上午9:56
 */
public final class RedisKeyConstant {

    /**
     * 普通标签关联商品key
     */
    public static final String SUBJECT_GOODS_MARK_LIST_KEY = "subject:goods:mark:subjectId_%s";
    /**
     * 权益活动标签关联商品key
     */
    public static final String SUBJECT_GOODS_EQUITY_MARK_LIST_KEY = "subject:goods:equity-mark:subjectId_%s_equityId_%s";
    /**
     * 权益标签关联商品key
     */
    public static final String SUBJECT_GOODS_EQUITY_LIST_KEY = "subject:goods:equity:subjectId_%s_equityId_%s";
    /**
     * 模版关联普通标签id
     */
    public static final String SUBJECT_GOODS_TAG_ID_KEY = "subject:goods:tag_id_key_subjectId_%s";
    /**
     * 模版关联权益标签id
     */
    public static final String SUBJECT_EQUITY_GOODS_TAG_ID_KEY = "subject:equity-goods:tag_id_key_subjectId_%s";

    /**
     * 模版关联权益活动id
     */
    public static final String SUBJECT_EQUITY_ID_KEY = "subject:equity-id:subjectId_%s";

    /**
     * 模版关联权益id
     */
    public static final String SUBJECT_EQUITY_KEY = "subject:equity:subjectId_%s";

    /**
     * 页面实例关联圈选信息key
     */
    public static final String SUBJECT_PAGE_TO_SELECT = "cms:page:select:select_type_%s_page_id_%s";

    /**
     * 圈选关联页面信息key
     */
    public static final String SUBJECT_SELECT_TO_PAGE = "cms:select:page:select_type_%s_select_id_%s";

}
