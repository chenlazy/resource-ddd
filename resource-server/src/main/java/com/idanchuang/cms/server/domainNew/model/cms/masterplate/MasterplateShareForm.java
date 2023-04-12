package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-17 15:16
 * @Desc: 模版分享信息
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MasterplateShareForm {

    /**
     * 分享用户等级
     */
    private Integer level;

    /**
     * 分享标题
     */
    private String shareTitle;

    /**
     * 分享描述
     */
    private String shareDesc;

    /**
     * 分享图片id
     */
    private Long shareImg;

    /**
     * 分享海报id
     */
    private Long sharePoster;

    /**
     * 分享海报url数组
     */
    private List<String> sharePosterList;

    /**
     * 分享范围 0：全部, 1:制定
     */
    private Integer shareScope;
}
