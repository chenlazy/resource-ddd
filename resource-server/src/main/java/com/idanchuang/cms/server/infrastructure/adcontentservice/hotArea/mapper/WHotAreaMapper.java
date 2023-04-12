package com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.entity.WHotArea;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.ListHotAreaRequest;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhousun
 * @since 2020-11-19
 */
public interface WHotAreaMapper extends BaseMapper<WHotArea> {

    /**
     * 分页查询
     *
     * @param page
     * @param request
     * @return
     */
    IPage<WHotArea> pageQueryHotArea(Page<WHotArea> page, @Param("request") ListHotAreaRequest request);

    /**
     * 查询有效行数
     * @param platform
     * @param displayPosition
     * @return
     */
    Integer queryLineCount(@Param("platform") Integer platform, @Param("displayPosition") String displayPosition);

}
