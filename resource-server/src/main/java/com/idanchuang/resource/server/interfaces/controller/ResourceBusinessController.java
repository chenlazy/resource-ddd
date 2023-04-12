package com.idanchuang.resource.server.interfaces.controller;

import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.facade.ResourceBusinessFacade;
import com.idanchuang.resource.api.response.ResourceBusinessRespDTO;
import com.idanchuang.resource.server.application.service.ResourceBusinessService;
import com.idanchuang.resource.server.application.service.ResourcePageService;
import com.idanchuang.resource.server.domain.model.resource.ResourceBusiness;
import com.idanchuang.resource.server.domain.model.resource.ResourcePage;
import com.idanchuang.resource.server.domain.repository.ArticleRepository;
import com.idanchuang.resource.server.infrastructure.persistence.model.ArticleSubjectDO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

import static com.idanchuang.resource.server.interfaces.assember.ResourceBusinessDtoAssembler.entityToDTO;

/**
 * @author wengbinbin
 * @date 2021/3/18
 */

@Api(tags = {"资源位业务页面模块"})
@RestController
@RequestMapping("/resource/business")
@Slf4j
public class ResourceBusinessController implements ResourceBusinessFacade {
    @Resource
    private ResourceBusinessService resourceBusinessService;
    @Resource
    private ResourcePageService resourcePageService;

    @Resource
    private ArticleRepository articleRepository;

    @Override
    public JsonResult<List<ResourceBusinessRespDTO>> getResourceBusiness() {
        List<ResourceBusinessRespDTO> respDTOS = new LinkedList<>();
        List<ResourceBusiness> resourceBusinessList = resourceBusinessService.getResourceBusiness();
        resourceBusinessList.forEach(resourceBusiness -> {
            List<ResourcePage> pages = resourcePageService.getResourcePageByBusinessId(resourceBusiness.getId());
            ResourceBusinessRespDTO dto = entityToDTO(resourceBusiness, pages);
            respDTOS.add(dto);
        });
        return JsonResult.success(respDTOS);
    }

    @Override
    public JsonResult<Object> getArticleById(Long id) {
        ArticleSubjectDO article = articleRepository.getArticleById(id);
        return JsonResult.success(article);
    }
}
