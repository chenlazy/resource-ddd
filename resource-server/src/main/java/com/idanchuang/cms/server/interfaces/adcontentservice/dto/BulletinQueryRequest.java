package com.idanchuang.cms.server.interfaces.adcontentservice.dto;

import com.idanchuang.component.base.page.PageDTO;
import lombok.Data;


/**
 * @author: xf
 * @time: 2020/12/3
 */
@Data
public class BulletinQueryRequest extends PageDTO {


    /**
     * 1,单创 2,abm
     */
    private Integer platformType;

    /**
     * app公告显示位置 单创:1,首页公告栏 2,购物车公告栏   ABM: 3,工作台公告栏
     */
    private Integer position;

    /**
     * 定向等级
     */
    private Integer level;

    /**
     * 1代表已经发布;0代表未发布
     */
    private Integer state;



}
