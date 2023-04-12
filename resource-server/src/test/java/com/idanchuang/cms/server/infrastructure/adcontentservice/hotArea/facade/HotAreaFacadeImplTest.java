package com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.facade;

import com.idanchuang.cms.api.adcontentservice.request.HotAreaPositionRequest;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;
import com.idanchuang.cms.server.application.adcontentservice.AppHotAreaFacade;
import com.idanchuang.component.base.JsonResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(HotAreaFacadeImpl.class)
public class HotAreaFacadeImplTest {

    @MockBean
    private AppHotAreaFacade mockAppHotAreaFacade;

    @Test
    public void testGetListByPositionPlatform() throws Exception {
        // Setup
        // Configure AppHotAreaFacade.getListByPositionPlatform(...).
        final HotAreaAdminDTO hotAreaAdminDTO = new HotAreaAdminDTO();
        hotAreaAdminDTO.setId(0);
        hotAreaAdminDTO.setTitle("title");
        hotAreaAdminDTO.setDisplayLine(0);
        hotAreaAdminDTO.setStartAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        hotAreaAdminDTO.setEndAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        hotAreaAdminDTO.setDisplayPosition("displayPosition");
        hotAreaAdminDTO.setDestLevel("destLevel");
        hotAreaAdminDTO.setLinePosition(0);
        hotAreaAdminDTO.setGoodsId(0);
        hotAreaAdminDTO.setShareUrl("shareUrl");
        final JsonResult<List<HotAreaAdminDTO>> listJsonResult = JsonResult.success(Arrays.asList(hotAreaAdminDTO));
        when(mockAppHotAreaFacade.getListByPositionPlatform(new HotAreaPositionRequest())).thenReturn(listJsonResult);
    }


    @Test
    public void testGetListByPositionPlatformNoTime() throws Exception {
        // Setup
        // Configure AppHotAreaFacade.getListByPositionPlatformNoTime(...).
        final HotAreaAdminDTO hotAreaAdminDTO = new HotAreaAdminDTO();
        hotAreaAdminDTO.setId(0);
        hotAreaAdminDTO.setTitle("title");
        hotAreaAdminDTO.setDisplayLine(0);
        hotAreaAdminDTO.setStartAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        hotAreaAdminDTO.setEndAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        hotAreaAdminDTO.setDisplayPosition("displayPosition");
        hotAreaAdminDTO.setDestLevel("destLevel");
        hotAreaAdminDTO.setLinePosition(0);
        hotAreaAdminDTO.setGoodsId(0);
        hotAreaAdminDTO.setShareUrl("shareUrl");
        final JsonResult<List<HotAreaAdminDTO>> listJsonResult = JsonResult.success(Arrays.asList(hotAreaAdminDTO));
        when(mockAppHotAreaFacade.getListByPositionPlatformNoTime("position", 0)).thenReturn(listJsonResult);
    }

}
