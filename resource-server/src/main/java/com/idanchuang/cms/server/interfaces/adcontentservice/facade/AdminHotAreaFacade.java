package com.idanchuang.cms.server.interfaces.adcontentservice.facade;

import com.google.common.collect.Lists;
import com.idanchuang.abmio.feign.dto.FileInfoDTO;
import com.idanchuang.cms.server.application.adcontentservice.RemoteGoodsAdContentService;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.remote.RemoteFileService;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.facade.AdminHotAreaFacadeContentService;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.NumberUtils;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.HotAreaRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.ListHotAreaRequest;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.HotAreaVO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.infrastructure.common.constant.ErrorBuilder;
import com.idanchuang.trade.goods.hulk.api.constant.SpuStatusEnum;
import com.idanchuang.trade.goods.hulk.api.entity.dto.goods.SpuInfoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhousun
 * @create 2020/11/30
 */
@Component
public class AdminHotAreaFacade {

    @Resource
    private AdminHotAreaFacadeContentService adminHotAreaFacadeContentService;
    @Resource
    private RemoteFileService remoteFileService;
    @Resource
    private RemoteGoodsAdContentService remoteGoodsAdContentService;


    public JsonResult<PageData<HotAreaVO>> listHotArea(ListHotAreaRequest request) {
        JsonResult<PageData<HotAreaAdminDTO>> jsonResult = adminHotAreaFacadeContentService.listHotArea(request);
        if (jsonResult.isSuccess()) {
            PageData<HotAreaAdminDTO> pageData = jsonResult.getData();
            List<HotAreaVO> records = convert(pageData.getRecords());
            PageData<HotAreaVO> voPageData = PageData.of(records, pageData.getCurrent(), pageData.getSize(), pageData.getTotal());
            return JsonResult.success(voPageData);
        }
        return JsonResult.failure(ErrorBuilder.of(jsonResult.getCode(), jsonResult.getMsg()));
    }

    private List<HotAreaVO> convert(List<HotAreaAdminDTO> records) {
        List<HotAreaVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(records)) {
            return result;
        }
        List<Integer> imageIdList = records.stream().map(HotAreaAdminDTO::getImage).collect(Collectors.toList());
        List<Integer> shareImageIdList = records.stream().map(HotAreaAdminDTO::getShareImage).collect(Collectors.toList());
        imageIdList.addAll(shareImageIdList);
        imageIdList.removeIf(Objects::isNull);
        Map<Long, FileInfoDTO> longFileInfoDTOMap = remoteFileService.batchQuery(NumberUtils.convertToLong(imageIdList));
        for (HotAreaAdminDTO dto : records) {
            HotAreaVO vo = new HotAreaVO();
            BeanUtils.copyProperties(dto, vo);
            HotAreaVO.ImageVO imageVO = new HotAreaVO.ImageVO();
            imageVO.setId(dto.getImage());
            FileInfoDTO infoDTO = longFileInfoDTOMap.get(dto.getImage().longValue());
            if (infoDTO != null) {
                imageVO.setFileUrl(infoDTO.getUrl());
            }
            vo.setImage(imageVO);
            Integer shareImageId = dto.getShareImage();
            if (shareImageId != null) {
                HotAreaVO.ImageVO shareImageVO = new HotAreaVO.ImageVO();
                shareImageVO.setId(shareImageId);
                FileInfoDTO infoDTO1 = longFileInfoDTOMap.get(shareImageId.longValue());
                if (infoDTO1 != null) {
                    shareImageVO.setFileUrl(infoDTO1.getUrl());
                }
                vo.setShareImage(shareImageVO);
            }
            result.add(vo);
        }
        return result;
    }


    public JsonResult<Integer> addHotArea(HotAreaRequest request) {
        checkJumpGoods(request);
        return adminHotAreaFacadeContentService.addHotArea(request);
    }


    public JsonResult<Integer> insertHotArea(HotAreaRequest request) {
        checkJumpGoods(request);
        return adminHotAreaFacadeContentService.insertHotArea(request);
    }

    public JsonResult<Integer> editHotArea(Integer id, HotAreaRequest request) {
        checkJumpGoods(request);
        return adminHotAreaFacadeContentService.editHotArea(id, request);
    }

    private void checkJumpGoods(HotAreaRequest request) {
        Integer hotType = request.getHotType();
        Integer jumpType = request.getJumpType();
        if (hotType != null && hotType == 1 && jumpType != null && jumpType == 1) {
            this.checkGoodsPutOn(request.getGoodsId().longValue());
        }
    }

    private void checkGoodsPutOn(Long spuId) {
        if (spuId == null) {
            return;
        }
        Map<Long, SpuInfoDTO> spuMap = remoteGoodsAdContentService.getSpuMapByIdList(Lists.newArrayList(spuId), Function.identity());
        SpuInfoDTO spuInfoDTO = spuMap.get(spuId);
        if (spuInfoDTO == null) {
            throw new BusinessException(ErrorBuilder.of(1001, "商品" + spuId + "不存在"));
        }
        if (!SystemConfig.getInstance().getCheckGoodsStatus()) {
            return;
        }
        String statusName = getSpuStatusName(spuInfoDTO);
        if (spuStatusMap.get(1).equals(statusName)) {
            return;
        }
        throw new BusinessException(ErrorBuilder.of(1002, "商品状态为" + statusName + "，无法添加"));
    }

    public JsonResult<Integer> deleteHotArea(Integer id) {
        return adminHotAreaFacadeContentService.deleteHotArea(id);
    }

    private static Map<Integer, String> spuStatusMap;

    static {
        spuStatusMap = new HashMap<>();
        spuStatusMap.put(SpuStatusEnum.DRAFT.getCode(), "草稿");
        spuStatusMap.put(SpuStatusEnum.PUF_ON.getCode(), "已上架");
        spuStatusMap.put(SpuStatusEnum.PUT_OFF.getCode(), "已下架");
    }

    private String getSpuStatusName(SpuInfoDTO spuDTO) {
        return spuStatusMap.get(spuDTO.getBaseSpuInfo().getStatus());
    }

}
