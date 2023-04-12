package com.idanchuang.cms.server.domain.model.cms;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.idanchuang.cms.api.response.ShareJsonModelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 渲染的页面
 * @author lei.liu
 * @date 2021/9/10
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageRender implements Serializable {
    private static final long serialVersionUID = 5867046142711596950L;

    /**
     * 页面定义ID
     */
    private Integer id;

    /**
     * 页面实例ID
     */
    private Integer pageId;

    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 页面标题
     */
    private String pageTitle;

    /**
     * 后台标题
     */
    private String backEndTitle;

    /**
     * 别名
     */
    private String aliasTitle;

    /**
     * 平台类型
     */
    private String platform;

    /**
     * 是否分享 1-是 0-否
     */
    private Integer shareFlag;

    /**
     * 当前版本
     */
    private String version;

    /**
     * 活动开始时间
     */
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;

    /**
     * 商品自动下架标记 0不下架 1下架'
     */
    private Integer goodsEnable;


    /**
     * 容器列表
     */
    private List<ContainerRender> containers;

    /**
     * 分享信息
     */
    private List<ShareJsonModelDTO> shareList;

    private LocalDateTime versionEndTime;

    /**
     * 下个版本号
     */
    private String nextVersion;

    private String sort;



    public static List<String> getSortArray (String sort) {


        if(StringUtils.isEmpty(sort)){
            return Lists.newArrayList();
        }

        try {
            JSONArray jsonSortArray = JSONArray.parseArray(sort);

            return jsonSortArray.toJavaList(String.class);
        } catch (Exception e) {
            return Collections.singletonList(sort);
        }

    }


    /**
     * 是否为页面在当前时间内投放的样式
     * @return  true:是；false:否
     */
    public boolean isCurrent() {
        if (startTime == null && endTime == null) {
            return true;
        }

        if (startTime != null && endTime == null) {
            return LocalDateTime.now().isAfter(startTime);
        }

        if (startTime == null) {
            return LocalDateTime.now().isBefore(endTime);
        }

        return LocalDateTime.now().compareTo(startTime) >= 0 && LocalDateTime.now().compareTo(endTime) < 0;
    }

    /**
     * 版本是否失效
     * @return   false：未失效，true：已失效
     */
    public boolean isOver() {
        if (versionEndTime == null || versionEndTime.compareTo(LocalDateTime.now()) > 0) {
            return false;
        }
        return true;
    }

}
