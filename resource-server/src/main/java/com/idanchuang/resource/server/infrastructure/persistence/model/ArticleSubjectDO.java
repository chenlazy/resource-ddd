package com.idanchuang.resource.server.infrastructure.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-29 17:43
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSubjectDO {

    private Long id;

    private Integer image;

    private String publishAt;

    private Integer likeCount;

    private String winTitle;

    private String content;

    private String title;

    private String subTitle;
}
