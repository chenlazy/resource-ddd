package com.idanchuang.cms.server.domainNew.model.cms.external.notice;

import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 11:15
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class PushMessageTemplate {

    private String title;

    private String link;

    private String content;

    private String imageUrl;

    private List<Integer> pushWays;

    private Long pushAt;

    private Long expireAt;

    private String batchId;

    private Integer platformId;

    private Integer channelId;

    private Integer redirectType;
}
