package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.api.facade.PageTagFacade;
import com.idanchuang.cms.api.request.PageTagConditionReq;
import com.idanchuang.cms.api.response.PageTagDTO;
import com.idanchuang.cms.server.application.service.PageTagService;
import com.idanchuang.cms.server.applicationNew.service.ClientPageService;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.*;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.interfaces.assember.InterfaceClientPageAssembler;
import com.idanchuang.cms.server.interfaces.web.config.RequestContext;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class PageTagFacadeImpl implements PageTagFacade {

    @Resource
    private PageTagService pageTagService;

    @Resource
    private ClientPageService clientPageService;

    @Resource
    private ClientPageRepository clientPageRepository;

    @Override
    public JsonResult<Boolean> insert(PageTagDTO pageTag) {
        Integer userId = RequestContext.get().getId();
        ClientPageForm clientPageForm = InterfaceClientPageAssembler.toClientPageForm(pageTag, userId);
        return JsonResult.success(clientPageService.createClientPage(clientPageForm));
    }

    @Override
    public JsonResult<Boolean> deleteById(Integer id) {
        return JsonResult.success(clientPageService.removeClientPage(new ClientPageId(id),
                new OperatorId(RequestContext.get().getId().longValue())));
    }

    @Override
    public JsonResult<Boolean> updateById(PageTagDTO pageTag) {
        Integer userId = RequestContext.get().getId();
        ClientPageForm clientPageForm = InterfaceClientPageAssembler.toClientPageForm(pageTag, userId);
        return JsonResult.success(clientPageService.updateClientPage(clientPageForm));
    }


    @Override
    public JsonResult<PageData<PageTagDTO>> pageByCondition(PageTagConditionReq condition) {
        ClientPageQueryForm clientPageQueryForm = InterfaceClientPageAssembler.toClientPageQueryForm(condition);
        PageData<ClientPage> clientPages = clientPageRepository.page(clientPageQueryForm);
        List<PageTagDTO> collect = clientPages.getRecords().stream()
                .map(InterfaceClientPageAssembler::toClientPageDTO)
                .collect(Collectors.toList());
        return JsonResult.success(PageData.of(collect, clientPages.getCurrent(), clientPages.getSize(), clientPages.getTotal()));
    }

    @Override
    public JsonResult<Boolean> update(Integer id) {
        // todo 这个接口什么用？是否还在用
        return JsonResult.success(pageTagService.updatePlatform(id));
    }

    @Override
    public JsonResult<PageTagDTO> selectById(Integer id) {
        ClientPage clientPage = clientPageRepository.query(new ClientPageId(id));
        PageTagDTO pageTagDTO = InterfaceClientPageAssembler.toClientPageDTO(clientPage);
        return JsonResult.success(pageTagDTO);
    }
}
