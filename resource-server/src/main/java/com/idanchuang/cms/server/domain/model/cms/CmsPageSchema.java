package com.idanchuang.cms.server.domain.model.cms;

import com.idanchuang.cms.server.domain.model.cms.schema.SchemaCode;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-03 14:38
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class CmsPageSchema {

    /**
     * 页面定义id
     */
    @NonFinal
    private Integer id;


    /**
     * 页面定义code
     */
    private SchemaCode schemaCode;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面类型
     */
    private Integer pageType;

    /**
     * 页面状态
     */
    private Integer status;

    /**
     * 页面发布版本
     */
    private String putVersions;

    /**
     * 操作人id
     */
    private Integer operatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    private Integer deleted;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 扩展信息
     */
    private SchemaExtra schemaExtra;

    public void setId(Integer id) {
        this.id = id;
    }
}
