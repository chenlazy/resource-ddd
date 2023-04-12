package com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.idanchuang.cms.server.application.constant.RedisKeyConstant;
import com.idanchuang.cms.server.application.remote.RemoteSsoService;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import com.idanchuang.sso.model.dto.system.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午1:59
 */
@Service
@Slf4j
public class CmsSelectRankService {

    @Resource
    private MasterplateRepository masterplateRepository;
    @Resource
    private CatalogueRepository catalogueRepository;
    @Resource
    private RemoteSsoService remoteSsoService;
    @Resource
    private CacheUtil cacheUtil;

    public List<CmsSelectRank> queryCmsSelectRankBySelectId(Integer selectType, Integer selectId) {
        String key = format(RedisKeyConstant.SUBJECT_SELECT_TO_PAGE, selectType, selectId);
        String forString = cacheUtil.getForString(key);
        List<Integer> pageList = StringUtils.isEmpty(forString) ? Lists.newArrayList() : JsonUtil.toList(forString, new TypeReference<List<Integer>>() {
        });
        if (CollectionUtils.isEmpty(pageList)) {
            return Lists.newArrayList();
        }
        List<MasterplateId> MasterplateIds = pageList.stream().map(MasterplateId::new).collect(Collectors.toList());
        //缓存中的关联模版信息
        List<Masterplate> cmsPages = masterplateRepository.selectMasterplateByIds(MasterplateIds);
        List<CatalogueId> pageSheSchemaIds = cmsPages.stream().map(Masterplate::getCatalogueId).collect(Collectors.toList());
        //查询有效的页面模版
        List<Catalogue> pageSchemaInfo = catalogueRepository.getCatalogueByIds(pageSheSchemaIds);
        Map<Long, Catalogue> pageSchemaMap = pageSchemaInfo.stream().collect(Collectors.toMap(u -> u.getId().getValue(), u -> u, (a, b) -> a));
        List<Long> collect = pageSchemaInfo.stream().map(u -> u.getId().getValue()).collect(Collectors.toList());
        List<Masterplate> realPageList = cmsPages.stream().filter(u -> collect.contains(u.getCatalogueId().getValue())).collect(Collectors.toList());
        List<Long> userIds = Lists.newArrayList();
        for (Masterplate masterplate : realPageList) {
            userIds.add(masterplate.getOperatorId().getValue());
            userIds.add(masterplate.getCreateId().getValue());
        }
        List<UserDetailDTO> users = remoteSsoService.getUsers(userIds);
        Map<Long, UserDetailDTO> usersMap = !CollectionUtils.isEmpty(users) ?
                users.stream().collect(Collectors.toMap(UserDetailDTO::getId, p -> p, (e1, e2) -> e1)) : Maps.newHashMap();
        List<CmsSelectRank> selectRankList = realPageList.stream().map(u -> {
            CmsSelectRank cmsSelectRank = new CmsSelectRank();
            cmsSelectRank.setPageId(u.getId().getValue());
            cmsSelectRank.setPageSchemaId(u.getCatalogueId().getValue());
            cmsSelectRank.setPageName(pageSchemaMap.get(u.getCatalogueId().getValue()) == null ? "" : pageSchemaMap.get(u.getCatalogueId().getValue()).getCatalogueName());
            cmsSelectRank.setAliasTitle(u.getAliasTitle());
            cmsSelectRank.setCreateId(u.getCreateId().getValue());
            cmsSelectRank.setOperatorId(u.getOperatorId().getValue());
            cmsSelectRank.setCreateName(usersMap.get(u.getCreateId().getValue()) == null ? "" : usersMap.get(u.getCreateId().getValue()).getRealName());
            cmsSelectRank.setOperatorName(usersMap.get(u.getOperatorId().getValue()) == null ? "" : usersMap.get(u.getOperatorId().getValue()).getRealName());
            return cmsSelectRank;
        }).collect(Collectors.toList());
        return selectRankList;
    }
}
