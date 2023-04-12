package com.idanchuang.cms.server.interfaces.web.controller;

import com.idanchuang.cms.api.request.GoodsMetaDataAddReq;
import com.idanchuang.cms.api.response.GoodsMetaDataDTO;
import com.idanchuang.cms.server.application.service.GoodsMetaDataService;
import com.idanchuang.cms.server.domain.repository.AbmauArticleRepository;
import com.idanchuang.cms.server.interfaces.web.vo.GoodsInfoVO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.infrastructure.persistence.model.ArticleSubjectDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-05 18:03
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/metadata")
@Api(value = "搭建元数据服务", tags = {"搭建元数据服务"})
@Slf4j
public class CmsMateDataController {

    @Resource
    private GoodsMetaDataService subjectGoodsMetadataService;

    @Resource
    private AbmauArticleRepository abmauArticleRepository;

    @PostMapping("/goods")
    @ApiOperation(value = "/goods", notes = "查询商品元数据信息，若传入现有的专题ID，组件ID，则保存商品至专题内", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<List<GoodsMetaDataDTO>> goodsMetaData(@RequestBody @Valid GoodsMetaDataAddReq metaDataAddReq) {
        GoodsInfoVO infoVO = new GoodsInfoVO(subjectGoodsMetadataService);
        infoVO.setReq(metaDataAddReq);
        // 校验商品是否重复
        infoVO.validGoodsExist();
        // 校验商品是否可以添加，根据商品的信息
        infoVO.validGoodsUsable();
        return JsonResult.success(infoVO.getMetadataList());
    }

    @ApiOperation(value = "查询文章")
    @GetMapping("/getArticleById")
    JsonResult<Object> getArticleById(@RequestParam("id") Long id){
        ArticleSubjectDO article = abmauArticleRepository.getArticleById(id);
        return JsonResult.success(article);
    }
}
