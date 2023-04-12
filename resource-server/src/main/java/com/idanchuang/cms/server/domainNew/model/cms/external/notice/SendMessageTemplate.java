package com.idanchuang.cms.server.domainNew.model.cms.external.notice;

import lombok.Data;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 11:18
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class SendMessageTemplate {

    private String title;

    private String content;

    private String link;

    private String imageUrl;

    private Integer platformId;

    private Integer templateId;

    private Long sendAt;

    private String batchId;

    private Integer channelId;

    private Integer redirectType;
}
