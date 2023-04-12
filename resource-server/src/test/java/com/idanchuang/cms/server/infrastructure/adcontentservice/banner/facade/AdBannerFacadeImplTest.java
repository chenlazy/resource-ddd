package com.idanchuang.cms.server.infrastructure.adcontentservice.banner.facade;

import com.idanchuang.cms.api.adcontentservice.request.AdBannerUrlRequest;
import com.idanchuang.cms.api.adcontentservice.response.AdBannerUrlDTO;
import com.idanchuang.cms.server.infrastructure.adcontentservice.banner.service.impl.IAdBannerService;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class AdBannerFacadeImplTest extends SpringTest {

    @MockBean
    private IAdBannerService mockAdBannerService;

    @Test
    public void testGetUrl() throws Exception {
        // Setup
        // Configure IAdBannerService.getUrl(...).
        final AdBannerUrlDTO adBannerUrlDTO = new AdBannerUrlDTO();
        adBannerUrlDTO.setBannerId(0);
        adBannerUrlDTO.setUrlTitle("urlTitle");
        adBannerUrlDTO.setUrl("url");
        adBannerUrlDTO.setWxUrl("wxUrl");
        adBannerUrlDTO.setLevel("level");
        adBannerUrlDTO.setStartAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        adBannerUrlDTO.setEndAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        adBannerUrlDTO.setImageId(0);
        adBannerUrlDTO.setImageUrl("imageUrl");
        adBannerUrlDTO.setSkipType(0);
        final List<AdBannerUrlDTO> adBannerUrlDTOS = Arrays.asList(adBannerUrlDTO);
        when(mockAdBannerService.getUrl(new AdBannerUrlRequest(), "cacheKey")).thenReturn(adBannerUrlDTOS);
    }
}
