package com.idanchuang.cms.server.domainNew.model.cms.aggregation.render;

import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-23 16:22
 * @Desc: 页面聚合工厂类
 * @Copyright VTN Limited. All rights reserved.
 */
public class PageRenderFactory {

    private PageRenderFactory() {

    }

    public static PageRender createPageRender(Masterplate masterplate, String aliasTitle, PlatformCode platform) {

        if (null == masterplate) {
            return null;
        }
        String version = masterplate.getId().getValue() + "" + masterplate.getVersion() + "";
        return new PageRender(masterplate.getCatalogueId(), masterplate.getId(), masterplate.getPageId(),
                masterplate.getAppTitle(), masterplate.getMasterplateName(), aliasTitle, platform,
                masterplate.getShareFlag(), version, null, masterplate.getStartTime(), masterplate.getEndTime(),
                masterplate.getExtra().getGoodsEnable(), masterplate.getPageStyle(), null, masterplate.getShareForms(),
                masterplate.getEndTime());
    }
}
