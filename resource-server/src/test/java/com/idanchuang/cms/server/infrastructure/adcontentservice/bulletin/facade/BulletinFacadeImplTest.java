package com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.facade;

import com.idanchuang.cms.api.adcontentservice.response.BulletinPortalDetailDTO;
import com.idanchuang.cms.server.interfaces.adcontentservice.facade.AdminBulletinFacade;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class BulletinFacadeImplTest extends SpringTest {

    @MockBean
    private AdminBulletinFacade mockAdminBulletinFacade;

    @Test
    public void testGetListByPlatFormPosition() throws Exception {
        // Setup
        // Configure AdminBulletinFacade.getListByPlatFormPosition(...).
        final BulletinPortalDetailDTO bulletinPortalDetailDTO = new BulletinPortalDetailDTO();
        bulletinPortalDetailDTO.setId(0);
        bulletinPortalDetailDTO.setPlatformType(0);
        bulletinPortalDetailDTO.setPosition(0);
        bulletinPortalDetailDTO.setTargetLevels("targetLevels");
        bulletinPortalDetailDTO.setTitle("title");
        bulletinPortalDetailDTO.setStartTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        bulletinPortalDetailDTO.setEndTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        bulletinPortalDetailDTO.setIsJump(0);
        bulletinPortalDetailDTO.setJumpType(0);
        bulletinPortalDetailDTO.setTextTitle("textTitle");
        final JsonResult<List<BulletinPortalDetailDTO>> listJsonResult = JsonResult.success(Arrays.asList(bulletinPortalDetailDTO));
        when(mockAdminBulletinFacade.getListByPlatFormPosition("platformType", "position")).thenReturn(listJsonResult);
    }

}
