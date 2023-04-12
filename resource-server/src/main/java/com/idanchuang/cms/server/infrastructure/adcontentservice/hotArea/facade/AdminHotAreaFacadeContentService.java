package com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.facade;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.entity.WHotArea;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.service.impl.IWHotAreaService;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.transfer.HotAreaTransfer;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.HotAreaRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.ListHotAreaRequest;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/2/23 2:31 下午
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class,transactionManager = "abmTransactionManager")
public class AdminHotAreaFacadeContentService {

    @Resource
    private IWHotAreaService hotAreaService;


    public JsonResult<PageData<HotAreaAdminDTO>> listHotArea(ListHotAreaRequest request) {
        Page<WHotArea> page = new Page<>(request.getCurrent(), request.getSize());
        IPage<HotAreaAdminDTO> pageDTO = hotAreaService.pageQueryHotArea(page, request).convert(HotAreaTransfer::transToDTO);
        List<HotAreaAdminDTO> records = pageDTO.getRecords();
        PageData<HotAreaAdminDTO> pageData = PageData.of(records, request.getCurrent(), request.getSize(), pageDTO.getTotal());
        return JsonResult.success(pageData);
    }

    public JsonResult<Integer> addHotArea(HotAreaRequest request) {
        return JsonResult.success(hotAreaService.save(request));
    }

    public JsonResult<Integer> insertHotArea(HotAreaRequest request) {
        return JsonResult.success(hotAreaService.insertHotArea(request));
    }

    public JsonResult<Integer> editHotArea(Integer id, HotAreaRequest request) {
        return JsonResult.success(hotAreaService.editHotArea(id, request));
    }


    public JsonResult<Integer> deleteHotArea(Integer id) {
        return JsonResult.success(hotAreaService.deleteHotArea(id));
    }
}
